package org.csstudio.opibuilder.actions;

import org.csstudio.opibuilder.OPIBuilderPlugin;
import org.csstudio.platform.ui.util.CustomMediaFactory;
import org.eclipse.gef.ui.actions.WorkbenchPartAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;


/**The action to make CSS full screen.
 * @author Xihui Chen
 *
 */
public class FullScreenAction extends WorkbenchPartAction {
	
	public static final String ID = "org.csstudio.opibuilder.actions.fullscreen";
	
	private ActionFactory.IWorkbenchAction toggleToolbarAction;
	private Menu menuBar;
	private boolean inFullScreenMode = false;
	private Shell shell;
	private ImageDescriptor fullScreenImage = 
		CustomMediaFactory.getInstance().getImageDescriptorFromPlugin(
			OPIBuilderPlugin.PLUGIN_ID, "icons/fullscreen.png");
	private ImageDescriptor exitFullScreenImage = 
		CustomMediaFactory.getInstance().getImageDescriptorFromPlugin(
			OPIBuilderPlugin.PLUGIN_ID, "icons/exitfullscreen.png");
	
	/**
	 * Constructor.
	 * @param part The workbench part associated with this PrintAction
	 */
	public FullScreenAction(IWorkbenchPart part) {
		super(part);
		 toggleToolbarAction = ActionFactory.TOGGLE_COOLBAR.create(
				 part.getSite().getWorkbenchWindow()); 
		 shell = part.getSite().getWorkbenchWindow().getShell();
		 menuBar = shell.getMenuBar();
		 setImageDescriptor(fullScreenImage);
		 
	}
	
	/**
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return true;
	}
	
	/**
	 * @see org.eclipse.gef.ui.actions.EditorPartAction#init()
	 */
	protected void init() {
		super.init();
		setText("Full Screen");
		setId(ID);
		}
	
	/**
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		if(inFullScreenMode){
			shell.setFullScreen(false);
			toggleToolbarAction.run();
			shell.setMenuBar(menuBar);		
			inFullScreenMode = false;
			setText("Full Screen");
			setImageDescriptor(fullScreenImage);
		}else {
			shell.setFullScreen(true);
			toggleToolbarAction.run();
			shell.setMenuBar(null);		
			inFullScreenMode = true;
			setText("Exit Full Screen");
			setImageDescriptor(exitFullScreenImage);
		}
	}

}
