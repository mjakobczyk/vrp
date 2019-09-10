package com.mjakobczyk.vrp.dynamic.impl.data.impl;

import com.mjakobczyk.location.Location;
import com.mjakobczyk.vrp.def.impl.data.impl.DefaultVrpFileDataProvider;
import com.mjakobczyk.vrp.dynamic.model.DynamicVrpInput;
import com.mjakobczyk.vrp.model.VrpInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * DynamicVrpDataProvider is an implementation of service providing data from file
 * to solve Dynamic VRP. It takes into consideration additional location occurrences.
 */
public class DynamicVrpFileDataProvider extends DefaultVrpFileDataProvider {

    @Override
    protected Optional<VrpInput> resolveVrpInputFromFileContent(final Stream<String> lines) {
        final List<Location> locations = new ArrayList<>();
        final List<Location> additionalLocations = new ArrayList<>();
        final List<String> stringLines = lines.collect(Collectors.toList());

        boolean shouldAddToAdditionalLocations = false;
        for (final String stringLine : stringLines) {
            if (stringLine.isEmpty()) {
                shouldAddToAdditionalLocations = true;
                continue;
            }

            final Location location = getLocationFromInputFileLine(stringLine);
            if (shouldAddToAdditionalLocations) {
                additionalLocations.add(location);
            } else {
                locations.add(location);
            }
        }

        return Optional.of(new DynamicVrpInput(locations, additionalLocations));
    }

    /**
     * Constructor of DynamicVrpDataProvider.
     */
    public DynamicVrpFileDataProvider() {
    }
}
