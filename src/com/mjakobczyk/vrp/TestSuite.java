package com.mjakobczyk.vrp;

import com.mjakobczyk.vrp.def.DefaultVrpDrawer;
import com.mjakobczyk.vrp.def.data.VrpFileDataProvider;
import com.mjakobczyk.vrp.def.solution.VrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.solution.impl.annealing.SimulatedAnnealingDynamicVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.AntColonyOptimizedDynamicVrpSolutionProviderStrategy;
import com.mjakobczyk.vrp.def.solution.impl.antcolony.model.AntColonyParameters;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpDataProvider;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpSolutionProvider;
import com.mjakobczyk.vrp.dynamic.impl.DynamicVrpSolver;
import com.mjakobczyk.vrp.dynamic.impl.data.impl.MendeleyDynamicVrpFileDataProvider;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpOutput;
import com.mjakobczyk.vrp.model.VrpOutput;
import com.mjakobczyk.vrp.service.VrpDataProvider;
import com.mjakobczyk.vrp.service.VrpDrawer;
import com.mjakobczyk.vrp.service.VrpSolutionProvider;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TestSuite creates an environment and performs various tests on basis of input.
 */
public class TestSuite {
    private static final Logger LOG = Logger.getLogger(String.valueOf(TestSuite.class));

    public static void main(final String[] args) {
        final TestSuite testSuite = new TestSuite();
        testSuite.runAntColony();
    }

    public void runAntColony() {
        // Prepare solver
        final DynamicVrpSolver solver = createDefaultVrpSolver();
        final String filesPrefix = "benchmark/";
        final List<String> files = listOfAllFiles();
        final VrpSolutionProviderStrategy strategy = new AntColonyOptimizedDynamicVrpSolutionProviderStrategy(new AntColonyParameters());
        changeStrategyTo(strategy, solver);
        final List<DynamicVrpOutput> resultingOutputs = new ArrayList<>();
        DynamicVrpOutput bestOutput;
        double bestCost = Double.MAX_VALUE;

        final List<String> antColonyExecutionLog = new ArrayList<>();

        adjustAntColonyParameters(new AntColonyParameters(), (AntColonyOptimizedDynamicVrpSolutionProviderStrategy) strategy);
        antColonyExecutionLog.add("Strategy: Ant Colony");

        for (final String fileName : files) {
            antColonyExecutionLog.add("Started processing file: " + fileName);
            LOG.log(Level.INFO, "Started processing file: " + fileName);

            changeInputFilePathTo(filesPrefix + fileName, solver);

            // Perform tests here
            final AntColonyParameters params = new AntColonyParameters();
            antColonyExecutionLog.add("Parameters: ant factor = " + params.getAntFactor() + " evaporation = " + params.getEvaporation());

            for (int i = 0; i < 5; ++i){

                final Optional<VrpOutput> optionalVrpOutput = solver.solve();

                if (optionalVrpOutput.isEmpty()) {
                    LOG.log(Level.SEVERE, "VrpOutput has not been collected.");
                } else {
                    LOG.log(Level.INFO, "VrpOutput has been obtained successfully.");
                    final DynamicVrpOutput dynamicVrpOutput = (DynamicVrpOutput) optionalVrpOutput.get();
                    resultingOutputs.add(dynamicVrpOutput);

                    antColonyExecutionLog.add("Cost = " + dynamicVrpOutput.getTotalCost() + " Vehicles = " + dynamicVrpOutput.getListOfListsOfLocations().size());

                    if (dynamicVrpOutput.getTotalCost() < bestCost) {
                        bestCost = dynamicVrpOutput.getTotalCost();
                        bestOutput = dynamicVrpOutput;
                    }
                }
            }

            final String prefix = "results/ant-colony-log-" + fileName;
            saveToFile(prefix, antColonyExecutionLog);
        }

        LOG.log(Level.INFO, "Best cost found: " + bestCost);

        final VrpDrawer vrpDrawer = new DefaultVrpDrawer();
//        vrpDrawer.draw(dynamicVrpOutput);
    }

    public void runSimulatedAnnealing() {
        // Prepare solver
        final DynamicVrpSolver solver = createDefaultVrpSolver();
        final String filesPrefix = "benchmark/";
        final List<String> files = listOfFiles();
        final VrpSolutionProviderStrategy strategy = new SimulatedAnnealingDynamicVrpSolutionProviderStrategy();
        changeStrategyTo(strategy, solver);
        final List<DynamicVrpOutput> resultingOutputs = new ArrayList<>();
        DynamicVrpOutput bestOutput;
        double bestCost = Double.MAX_VALUE;

        final List<String> simulatedAnnealingExecutionLog = new ArrayList<>();

        simulatedAnnealingExecutionLog.add("Strategy: Ant Colony");

        for (final String fileName : files) {
            simulatedAnnealingExecutionLog.add("Started processing file: " + fileName);
            LOG.log(Level.INFO, "Started processing file: " + fileName);

            changeInputFilePathTo(filesPrefix + fileName, solver);
            for (int i = 0; i < 5; ++i) {
                final Optional<VrpOutput> optionalVrpOutput = solver.solve();

                if (optionalVrpOutput.isEmpty()) {
                    LOG.log(Level.SEVERE, "VrpOutput has not been collected.");
                } else {
                    LOG.log(Level.INFO, "VrpOutput has been obtained successfully.");
                    final DynamicVrpOutput dynamicVrpOutput = (DynamicVrpOutput) optionalVrpOutput.get();
                    resultingOutputs.add(dynamicVrpOutput);
                    simulatedAnnealingExecutionLog.add("Cost = " + dynamicVrpOutput.getTotalCost());

                    if (dynamicVrpOutput.getTotalCost() < bestCost) {
                        bestCost = dynamicVrpOutput.getTotalCost();
                        bestOutput = dynamicVrpOutput;
                    }
                }
            }
            final String prefix = "results/sa-log-" + fileName;
            saveToFile(prefix, simulatedAnnealingExecutionLog);
        }

        LOG.log(Level.INFO, "Best cost found: " + bestCost);

        final VrpDrawer vrpDrawer = new DefaultVrpDrawer();
//        vrpDrawer.draw(dynamicVrpOutput);
    }

    public DynamicVrpSolver createDefaultVrpSolver() {
        final VrpFileDataProvider fileDataProvider = new MendeleyDynamicVrpFileDataProvider();
        final VrpDataProvider vrpDataProvider = new DynamicVrpDataProvider("", fileDataProvider);
        final VrpSolutionProviderStrategy strategy = new SimulatedAnnealingDynamicVrpSolutionProviderStrategy();
        final VrpSolutionProvider vrpSolutionProvider = new DynamicVrpSolutionProvider(strategy);
        return new DynamicVrpSolver(vrpDataProvider, vrpSolutionProvider);
    }

    protected void changeInputFilePathTo(final String inputFilePath, final DynamicVrpSolver solver) {
        final VrpFileDataProvider fileDataProvider = new MendeleyDynamicVrpFileDataProvider();
        final VrpDataProvider vrpDataProvider = new DynamicVrpDataProvider(inputFilePath, fileDataProvider);
        solver.setVrpDataProvider(vrpDataProvider);
    }

    protected void changeStrategyTo(final VrpSolutionProviderStrategy strategy, final DynamicVrpSolver solver) {
        final VrpSolutionProvider vrpSolutionProvider = new DynamicVrpSolutionProvider(strategy);
        solver.setVrpSolutionProvider(vrpSolutionProvider);
    }

    protected void adjustAntColonyParameters(final AntColonyParameters parameters, final AntColonyOptimizedDynamicVrpSolutionProviderStrategy strategy) {
        strategy.setParameters(parameters);
    }

    protected List<String> listOfFiles() {
        return Arrays.asList("c50D.vrp", "c75D.vrp", "c100D.vrp", "c120D.vrp",
                "c150D.vrp", "c199D.vrp", "f71D.vrp", "f134D.vrp", "tai75aD.vrp", "tai100aD.vrp",
                "tai150aD.vrp");
//        return Arrays.asList("c50D.vrp");
    }

    protected List<String> listOfAdditionalFiles() {
        return Arrays.asList("c100bD.vrp", "tai75bD.vrp", "tai75cD.vrp", "tai75dD.vrp",
                "tai100bD.vrp", "tai100cD.vrp", "tai100dD.vrp",
                "tai150bD.vrp", "tai150cD.vrp", "tai150dD.vrp");
    }

    protected List<String> listOfAllFiles() {
        return Arrays.asList("c50D.vrp", "c75D.vrp", "c100D.vrp", "c120D.vrp",
                "c150D.vrp", "c199D.vrp", "f71D.vrp", "f134D.vrp", "tai75aD.vrp", "tai100aD.vrp",
                "tai150aD.vrp", "c100bD.vrp", "tai75bD.vrp", "tai75cD.vrp", "tai75dD.vrp",
                "tai100bD.vrp", "tai100cD.vrp", "tai100dD.vrp",
                "tai150bD.vrp", "tai150cD.vrp", "tai150dD.vrp");
    }

    protected List<String> bigFile() {
        return Arrays.asList("big_tai385.vrp");
    }

    protected List<AntColonyParameters> listOfParameters() {
        final List<AntColonyParameters> parametersList = new ArrayList<>();
        final AntColonyParameters params1 = new AntColonyParameters();
        params1.setAntFactor(0.5);
        parametersList.add(params1);
        final AntColonyParameters params2 = new AntColonyParameters();
        params2.setAntFactor(1.0);
        parametersList.add(params2);
        final AntColonyParameters params3 = new AntColonyParameters();
        params3.setAntFactor(2.0);
        parametersList.add(params3);
        final AntColonyParameters params4 = new AntColonyParameters();
        params4.setEvaporation(0.8);
        parametersList.add(params4);
        final AntColonyParameters params5 = new AntColonyParameters();
        params5.setEvaporation(0.5);
        parametersList.add(params5);
        final AntColonyParameters params6 = new AntColonyParameters();
        params6.setEvaporation(0.2);
        parametersList.add(params6);

        return parametersList;
    }

    protected void saveToFile(final String prefix, final List<String> content) {
        final LocalDateTime ldt = LocalDateTime.now();
        final String time = ldt.getYear() + "-" + ldt.getMonthValue() + "-" + ldt.getDayOfMonth() + "-"
                + ldt.getHour() + "-" + ldt.getMinute() + "-" + ldt.getSecond();
        final String suffix = ".txt";

        final String fileName = prefix+time+suffix;
        final File file = new File(fileName);

        try {
            file.createNewFile();
            Files.write(Paths.get(fileName), content, StandardCharsets.UTF_8);

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
