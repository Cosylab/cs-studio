package org.csstudio.perspectives;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;

public class PerspectiveStartup implements IStartup {

    /**
     * Create a perspective saver on startup.
     */
    @Override
    public void earlyStartup() {
        IEclipseContext context = PlatformUI.getWorkbench().getService(IEclipseContext.class);
        context.set(IPerspectiveUtils.class.getCanonicalName(), new PerspectiveUtils());
        // Initialise a perspective saver.
        ContextInjectionFactory.make(PerspectiveSaver.class, context);
        // Load perspectives at startup.
        PerspectiveLoader loader = ContextInjectionFactory.make(PerspectiveLoader.class, context);
        try {
            File f = getPerspectivesDirectory();
            loader.loadFromDirectory(f);
        } catch (IOException | URISyntaxException e) {
            Plugin.getLogger().log(Level.WARNING, Messages.PerspectiveStartup_startupLoadFailed, e);
        }
    }

    private File getPerspectivesDirectory() throws MalformedURLException, IOException, URISyntaxException {
        URL directoryUrl = FileLocator.resolve(new URL(Plugin.PERSPECTIVE_LOAD_LOCATION));
        System.out.println(directoryUrl);
        return new File(directoryUrl.toURI());
    }

}
