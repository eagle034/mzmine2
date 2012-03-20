/*
 * Copyright 2006-2012 The MZmine 2 Development Team
 * 
 * This file is part of MZmine 2.
 * 
 * MZmine 2 is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * MZmine 2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * MZmine 2; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */

package net.sf.mzmine.modules.peaklistmethods.dataanalysis.rtmzplots.logratioplot;

import java.awt.Color;
import java.util.Collection;

import javax.annotation.Nonnull;

import net.sf.mzmine.data.PeakList;
import net.sf.mzmine.main.MZmineCore;
import net.sf.mzmine.modules.MZmineModuleCategory;
import net.sf.mzmine.modules.MZmineProcessingModule;
import net.sf.mzmine.modules.peaklistmethods.dataanalysis.rtmzplots.RTMZAnalyzerWindow;
import net.sf.mzmine.modules.peaklistmethods.dataanalysis.rtmzplots.cvplot.CVParameters;
import net.sf.mzmine.parameters.ParameterSet;
import net.sf.mzmine.taskcontrol.Task;
import net.sf.mzmine.util.ExitCode;
import net.sf.mzmine.util.interpolatinglookuppaintscale.InterpolatingLookupPaintScale;

import org.jfree.data.xy.AbstractXYZDataset;

public class LogratioPlotModule implements MZmineProcessingModule {

    private static final String MODULE_NAME = "Logratio analysis";
    private static final String MODULE_DESCRIPTION = "Logratio analysis"; // TODO

    @Override
    public String getName() {
	return MODULE_NAME;
    }

    @Override
    public String getDescription() {
	return MODULE_DESCRIPTION;
    }

    @Override
    @Nonnull
    public ExitCode runModule(@Nonnull ParameterSet parameters,
	    @Nonnull Collection<Task> tasks) {

	PeakList peakLists[] = parameters.getParameter(CVParameters.peakLists)
		.getValue();

	for (PeakList pl : peakLists) {

	    // Create dataset & paint scale
	    AbstractXYZDataset dataset = new LogratioDataset(pl, parameters);
	    InterpolatingLookupPaintScale paintScale = new InterpolatingLookupPaintScale();
	    paintScale.add(-1.00, new Color(0, 255, 0));
	    paintScale.add(0.00, new Color(0, 0, 0));
	    paintScale.add(1.00, new Color(255, 0, 0));

	    // Create & show window
	    RTMZAnalyzerWindow window = new RTMZAnalyzerWindow(dataset, pl,
		    paintScale);
	    MZmineCore.getDesktop().addInternalFrame(window);

	}

	return ExitCode.OK;
    }

    @Override
    public MZmineModuleCategory getModuleCategory() {
	return MZmineModuleCategory.DATAANALYSIS;
    }

    @Override
    public Class<? extends ParameterSet> getParameterSetClass() {
	return LogratioParameters.class;
    }

}