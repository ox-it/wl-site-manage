package org.sakaiproject.site.tool.helper.managegroupsectionrole.rsf;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.site.api.Group;
import org.sakaiproject.site.tool.helper.managegroupsectionrole.impl.SiteManageGroupSectionRoleHandler;

import uk.org.ponder.messageutil.MessageLocator;
import uk.org.ponder.messageutil.TargettedMessageList;
import uk.org.ponder.rsf.components.*;
import uk.org.ponder.rsf.components.decorators.UICSSDecorator;
import uk.org.ponder.rsf.components.decorators.UILabelTargetDecorator;
import uk.org.ponder.rsf.flow.ARIResult;
import uk.org.ponder.rsf.flow.ActionResultInterceptor;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;

public class CreateJoinableGroupsProducer implements ViewComponentProducer, ActionResultInterceptor, ViewParamsReporter{

	public static final String VIEW_ID = "CreateJoinableGroups";
	private static Log M_log = LogFactory.getLog(CreateJoinableGroupsProducer.class);
	public SiteManageGroupSectionRoleHandler handler;
	public MessageLocator messageLocator;
		
	private TargettedMessageList tml;
	public void setTargettedMessageList(TargettedMessageList tml) {
		this.tml = tml;
	}
	
	@Override
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {
		String joinableSetId = ((CreateJoinableGroupViewParameters) viewparams).id;
		boolean edit = joinableSetId != null && !"".equals(joinableSetId);
		if(edit){
			handler.joinableSetName = joinableSetId;
			handler.joinableSetNameOrig = joinableSetId;
		}
		UIForm groupForm = UIForm.make(tofill, "groups-form");
		String title = null;
		if(edit){
			title = messageLocator.getMessage("group.joinable.title.edit");
		}else{
			title = messageLocator.getMessage("group.joinable.title");
		}
		UIOutput.make(groupForm, "prompt", title);
        UIOutput.make(groupForm, "emptyGroupTitleAlert", messageLocator.getMessage("editgroup.titlemissing"));
        UIOutput.make(groupForm, "instructions", messageLocator.getMessage("group.joinable.desc"));
        UILabelTargetDecorator.targetLabel(UIMessage.make(groupForm, "group-title-group", "group.joinable.setname"), UIInput.make(groupForm, "groupTitle-group", "SiteManageGroupSectionRoleHandler.joinableSetName"));
        UIInput.make(groupForm, "groupTitle-group-orig", "SiteManageGroupSectionRoleHandler.joinableSetNameOrig");
        
        if(edit){
        	UIOutput.make(groupForm, "current-groups-title", messageLocator.getMessage("group.joinable.currentgroups"));
        	int i = 0;
        	for(Group group : handler.site.getGroups()){
        		String joinableSet = group.getProperties().getProperty(group.GROUP_PROP_JOINABLE_SET);
        		if(joinableSet != null && joinableSet.equals(joinableSetId)){
        			UIBranchContainer currentGroupsRow = UIBranchContainer.make(tofill,"current-groups-row:", Integer.valueOf(i).toString());
        			UIOutput.make(currentGroupsRow, "current-group", group.getTitle());
        			i++;
        		}
        	}
        	
        }
        Map<String,String> cssMap = new HashMap<String,String>();
		cssMap.put("background","#FFFFCC");
		if(edit){
			//Additional Row
			UIBranchContainer additionalRow = UIBranchContainer.make(groupForm,"additional-title-row:");
			additionalRow.decorate(new UICSSDecorator(cssMap));
			UIOutput.make(additionalRow, "additional-title", messageLocator.getMessage("group.joinable.additionalGroups"));
		}
		//Num of Groups Row
        UIBranchContainer groupsRow = UIBranchContainer.make(groupForm,"num-groups-row:");
        if(edit){
        	groupsRow.decorate(new UICSSDecorator(cssMap));
        }
        UILabelTargetDecorator.targetLabel(UIMessage.make(groupsRow, "group-unit", "group.joinable.numOfGroups"), UIInput.make(groupsRow, "num-groups", "SiteManageGroupSectionRoleHandler.joinableSetNumOfGroups"));
        //Max members Row:
        UIBranchContainer maxRow = UIBranchContainer.make(groupForm,"max-members-row:");
        if(edit){
        	maxRow.decorate(new UICSSDecorator(cssMap));
        }
        UILabelTargetDecorator.targetLabel(UIMessage.make(maxRow, "group-max-members", "group.joinable.maxMembers"), UIInput.make(maxRow, "num-max-members", "SiteManageGroupSectionRoleHandler.joinableSetNumOfMembers"));
        //allow preview row:
        UIBranchContainer allowViewRow = UIBranchContainer.make(groupForm,"allowview-row:");
        if(edit){
        	allowViewRow.decorate(new UICSSDecorator(cssMap));
        }
        UIBoundBoolean checkbox = UIBoundBoolean.make(allowViewRow, "allowViewMembership", "#{SiteManageGroupSectionRoleHandler.allowViewMembership}");
		UILabelTargetDecorator.targetLabel(UIMessage.make(allowViewRow, "allowViewMembership-label", "group.joinable.allowPreview"), checkbox);
		if(edit){
			//Generate Button
			UIBranchContainer generateRow = UIBranchContainer.make(groupForm,"generate-row:");
			generateRow.decorate(new UICSSDecorator(cssMap));
			UICommand.make(generateRow, "gererate", messageLocator.getMessage("group.joinable.generate"), "#{SiteManageGroupSectionRoleHandler.processGenerateJoinableSet}");
		}
		//Save/Cancel
		String saveMethodBinding = null;
		if(edit){
			saveMethodBinding = "#{SiteManageGroupSectionRoleHandler.processChangeJoinableSetName}";
		}else{
			saveMethodBinding = "#{SiteManageGroupSectionRoleHandler.processCreateJoinableSet}";
		}
		UICommand.make(groupForm, "save", messageLocator.getMessage("update"), saveMethodBinding);
        UICommand cancel = UICommand.make(groupForm, "cancel", messageLocator.getMessage("cancel"), "#{SiteManageGroupSectionRoleHandler.processBack}");
        cancel.parameters.add(new UIDeletionBinding("#{destroyScope.resultScope}"));
        
        if(edit){
        	//Delete Set button:
        	//UICommand.make(groupForm, "delete", messageLocator.getMessage("group.joinable.delete"), "#{SiteManageGroupSectionRoleHandler.processDeleteJoinableSet}");
			UIForm deleteGroupsForm = UIForm.make(tofill, "delete-groups-form",new JoinableGroupDelViewParameters(JoinableGroupDelProducer.VIEW_ID, joinableSetId));
			UICommand.make(deleteGroupsForm, "delete", messageLocator.getMessage("group.joinable.delete"));
        }
      //process any messages
        tml = handler.messages;
        if (tml.size() > 0) {
			for (int i = 0; i < tml.size(); i ++ ) {
				UIBranchContainer errorRow = UIBranchContainer.make(groupForm,"error-row:", Integer.valueOf(i).toString());
				String outString = "";
				if (tml.messageAt(i).args != null ) {
					outString = messageLocator.getMessage(tml.messageAt(i).acquireMessageCode(),tml.messageAt(i).args[0]);
				} else {
					outString = messageLocator.getMessage(tml.messageAt(i).acquireMessageCode());
				}
				UIOutput.make(errorRow,"error",outString);
		    		
			}
        }
	}

	@Override
	public String getViewID() {
		return VIEW_ID;
	}

	@Override
	public ViewParameters getViewParameters() {
		CreateJoinableGroupViewParameters params = new CreateJoinableGroupViewParameters();
		params.id = null;
		return params;
	}

	@Override
	public void interceptActionResult(ARIResult result,
			ViewParameters incoming, Object actionReturn) {
		if ("success".equals(actionReturn) || "cancel".equals(actionReturn)) {
            result.resultingView = new SimpleViewParameters(GroupListProducer.VIEW_ID);
        }
	}

}
