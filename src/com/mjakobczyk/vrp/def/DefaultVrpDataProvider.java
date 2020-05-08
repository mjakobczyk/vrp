package com.mjakobczyk.vrp.def;

import com.mjakobczyk.vrp.service.VrpDataProvider;
import com.mjakobczyk.vrp.def.data.impl.DefaultVrpFileDataProvider;
import com.mjakobczyk.vrp.def.data.VrpFileDataProvider;
import com.mjakobczyk.vrp.model.VrpInput;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DefaultVrpDataProvider is a default implementation of data provider for solving VRP.
 * It works as a proxy calling another predefined VrpDataProvider.
 */
public class DefaultVrpDataProvider implements VrpDataProvider {

    /**
     * DefaultVrpDataProvider logger, providing data about runtime behaviour.
     */
    private static final Logger LOG = Logger.getLogger(String.valueOf(DefaultVrpDataProvider.class));

    /**
     * Name of the file containing VRP data.
     */
    private final String vrpDataFileName;

    /**
     * VrpFileDataProvider is used as a default data provider.
     */
    private VrpFileDataProvider vrpFileDataProvider;

    @Override
    public Optional<VrpInput> getVrpInput() {
        Optional<File> optionalFile = getVrpFileDataProvider().resolveFileFromResources(this.vrpDataFileName);

        if (optionalFile.isEmpty()) {
            optionalFile = getVrpFileDataProvider().resolveFileFromPath(this.vrpDataFileName);

            if (optionalFile.isEmpty()) {
                LOG.log(Level.INFO, "Provided file: " + " does not contain any data or does not exist.");
                return Optional.empty();
            }
        }

        final String filePath = optionalFile.get().getAbsolutePath();

        try {
            final Optional<VrpInput> optionalVrpInput = getVrpFileDataProvider().resolveVrpInputDataFromFile(optionalFile.get());

            if (optionalVrpInput.isEmpty()) {
                LOG.log(Level.SEVERE, "No VrpInput was provided from file {0}!", filePath);
            }

            return optionalVrpInput;
        } catch (final IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Constructor of DefaultVrpDataProvider.
     */
    public DefaultVrpDataProvider() {
        this.vrpFileDataProvider = new DefaultVrpFileDataProvider();
        this.vrpDataFileName = StringUtils.EMPTY;
    }

    /**
     * Constructor of DefaultVrpDataProvider.
     *
     * @param vrpDataFileName to be read
     */
    public DefaultVrpDataProvider(final String vrpDataFileName) {
        this.vrpFileDataProvider = new DefaultVrpFileDataProvider();
        this.vrpDataFileName = vrpDataFileName;
    }

    /**
     * Constructor of DefaultVrpDataProvider.
     *
     * @param vrpDataFileName     to be read
     * @param vrpFileDataProvider for reading data
     */
    public DefaultVrpDataProvider(final String vrpDataFileName, final VrpFileDataProvider vrpFileDataProvider) {
        this.vrpDataFileName = vrpDataFileName;
        this.vrpFileDataProvider = vrpFileDataProvider;
    }

    /**
     * Getter of VrpFileDataProvider.
     *
     * @return VrpFileDataProvider object
     */
    protected VrpFileDataProvider getVrpFileDataProvider() {
        return vrpFileDataProvider;
    }

    /**
     * Setter of VrpFileDataProvider.
     *
     * @param vrpFileDataProvider to set
     */
    public void setVrpFileDataProvider(final VrpFileDataProvider vrpFileDataProvider) {
        this.vrpFileDataProvider = vrpFileDataProvider;
    }

}
