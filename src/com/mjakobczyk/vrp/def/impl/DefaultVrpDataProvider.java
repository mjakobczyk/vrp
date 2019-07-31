package com.mjakobczyk.vrp.def.impl;

import com.mjakobczyk.vrp.VrpDataProvider;
import com.mjakobczyk.vrp.def.impl.data.DefaultVrpFileDataProvider;
import com.mjakobczyk.vrp.def.impl.data.VrpFileDataProvider;
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
     * Name of the file that contains VRP data.
     */
    private static final String DEFAULT_VRP_DATA_FILE_NAME = "defaultVrpDataFile";

    /**
     * Name of the file containing VRP data.
     */
    private final String fileName;

    /**
     * VrpFileDataProvider is used as a default data provider.
     */
    private VrpFileDataProvider vrpFileDataProvider;

    @Override
    public Optional<VrpInput> getVrpInput() {
        final Optional<File> optionalFile;

        if (this.fileName.isEmpty()) {
            optionalFile = getVrpFileDataProvider().resolveFileFromResources(DefaultVrpDataProvider.DEFAULT_VRP_DATA_FILE_NAME);
        } else {
            optionalFile = getVrpFileDataProvider().resolveFileFromPath(this.fileName);
        }

        if (optionalFile.isEmpty()) {
            LOG.log(Level.INFO, "Provided default VRP data file does not contain data or does not exist.");
            return Optional.empty();
        }

        final String filePath = optionalFile.get().getAbsolutePath();

        try {
            final Optional<VrpInput> optionalVrpInput = getVrpFileDataProvider().resolveVrpInputDataFromFile(optionalFile.get());

            if (optionalVrpInput.isEmpty()) {
                LOG.log(Level.SEVERE, this.getClass().getName() + " - no VrpInput was provided from file " + filePath + "!");
            }

            return optionalVrpInput;
        } catch (final IOException e) {
            LOG.log(Level.SEVERE, this.getClass().getName() + " - fatal error reading file " + filePath + "!");
        }

        return Optional.empty();
    }

    /**
     * Constructor of DefaultVrpDataProvider.
     */
    public DefaultVrpDataProvider() {
        this.vrpFileDataProvider = new DefaultVrpFileDataProvider();
        this.fileName = StringUtils.EMPTY;
    }

    /**
     * Constructor of DefaultVrpDataProvider.
     *
     * @param fileName to be read
     */
    public DefaultVrpDataProvider(final String fileName) {
        this.vrpFileDataProvider = new DefaultVrpFileDataProvider();
        this.fileName = fileName;
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
