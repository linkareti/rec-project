WikiEditor.prototype.getEditorTemplate=function(C,H){var B=new Array();
var G='		<table class="mceEditor" border="0" cellpadding="0" cellspacing="0" width="{$width}" height="{$height}"><tbody>		<tr><td class="mceToolbar" align="center" height="1">';
var E=tinyMCE.getParam("wiki_editor_toolbar");
var F=E.split(",");
for(var A=0;
A<F.length;
A++){var D=this.trimString(F[A]);
switch(D){case"texttoolbar":G+="<span id='{$editor_id}_texttoolbar'>"+this.getTextToolbar()+"</span>";
break;
case"justifytoolbar":G+="<span id='{$editor_id}_justifytoolbar'>"+this.TOOLBAR_SPACER+this.getJustifyToolbar()+"</span>";
break;
case"listtoolbar":G+="<span id='{$editor_id}_listtoolbar'>"+this.TOOLBAR_SPACER+this.getListToolbar()+"</span>";
break;
case"indenttoolbar":G+="<span id='{$editor_id}_indenttoolbar'>"+this.TOOLBAR_SPACER+this.getTabToolbar()+"</span>";
break;
case"undotoolbar":G+="<span id='{$editor_id}_undotoolbar'>"+this.TOOLBAR_SPACER+this.getUndoToolbar()+"</span>";
break;
case"titletoolbar":G+="<span id='{$editor_id}_titletoolbar'>"+this.TOOLBAR_SPACER+this.getTitleToolbar()+"</span>";
break;
case"styletoolbar":G+="<span id='{$editor_id}_styletoolbar'>"+this.TOOLBAR_SPACER+this.getStyleToolbar()+"</span>";
break;
case"horizontaltoolbar":G+="<span id='{$editor_id}_horizontaltoolbar'>"+this.TOOLBAR_SPACER+this.getHorizontalruleControls()+this.getRemoveformatControls()+"</span>";
break;
case"symboltoolbar":G+="<span id='{$editor_id}_symboltoolbar'>"+this.getSymbolToolbar()+"</span>";
break;
case"suptoolbar":G+="<span id='{$editor_id}_suptoolbar'>"+this.TOOLBAR_SPACER+this.getSupAndSubToolbar()+"</span>";
break;
case"tabletoolbar":G+="<span id='{$editor_id}_tabletoolbar'>"+this.TOOLBAR_SPACER+this.getTableToolbar()+"</span>";
break;
case"tablerowtoolbar":G+="<span id='{$editor_id}_tablerowtoolbar'>"+this.TOOLBAR_SPACER+this.getTableRowToolbar()+"</span>";
break;
case"tablecoltoolbar":G+="<span id='{$editor_id}_tablecoltoolbar'>"+this.TOOLBAR_SPACER+this.getTableColToolbar()+"</span>";
break;
case"linktoolbar":G+="<span id='{$editor_id}_linktoolbar'>"+this.TOOLBAR_SPACER+this.getLinkToolbar()+"</span>";
break;
case"attachmenttoolbar":if(this.isPluginLoaded("attachments")){G+="<span id='{$editor_id}_attachmenttoolbar'>"+this.TOOLBAR_SPACER+this.getAttachmentsToolbar()+"</span>"
}break;
case"macrostoolbar":if(this.isPluginLoaded("macros")){G+="<span id='{$editor_id}_macrostoolbar'>"+this.getMacrosToolbar()+"</span>"
}break;
case"togglebutton":G+="<span id='{$editor_id}_togglebutton'>"+this.getToggleButton()+"</span>";
break
}}G+='</td></tr>			<tr><td align="center">		    <span id="{$editor_id}">IFRAME</span>		    </td></tr>            <tr><td>            <textarea name="{$editor_id}_content" id="{$editor_id}_content" class="mceEditorSource" cols="98" rows="23" style="display:none"></textarea>            </td></tr>            </tbody></table>';
B.html=G;
B.delta_width=0;
B.delta_height=-40;
return B
};
WikiEditor.prototype.TOOLBAR_SPACER=' <img src="{$themeurl}/images/spacer.gif" width="1" height="15" class="mceSeparatorLine">';
WikiEditor.prototype.createButtonHTML=function(G,A,F,D,C,E,B){D="tinyMCE.execInstanceCommand('{$editor_id}','"+D+"'";
if(typeof (C)!="undefined"&&C!=null){D+=","+C
}if(typeof (E)!="undefined"&&E!=null){D+=",'"+E+"'"
}D+=");";
if(typeof (B)=="undefined"){return'<a id="{$editor_id}_'+G+'" href="javascript:'+D+'" onclick="'+D+'return false;" onmousedown="return false;" class="mceButtonNormal" target="_self"><img src="{$themeurl}/images/'+A+'" title="{$'+F+'}" /></a>'
}else{}};
tinyMCE.addToLang("",{theme_style_select:"-- Styles --",theme_code_desc:"Edit HTML Source",theme_code_title:"HTML Source Editor",theme_code_wordwrap:"Word wrap",theme_sub_desc:"Subscript",theme_sup_desc:"Superscript",theme_hr_desc:"Insert horizontal ruler",theme_removeformat_desc:"Remove formatting",theme_custom1_desc:"Your custom description here",insert_image_border:"Border",insert_image_dimensions:"Dimensions",insert_image_vspace:"Vertical space",insert_image_hspace:"Horizontal space",insert_image_align:"Alignment",insert_image_align_default:"-- Not set --",insert_image_align_baseline:"Baseline",insert_image_align_top:"Top",insert_image_align_middle:"Middle",insert_image_align_bottom:"Bottom",insert_image_align_texttop:"TextTop",insert_image_align_absmiddle:"Absolute Middle",insert_image_align_absbottom:"Absolute Bottom",insert_image_align_left:"Left",insert_image_align_right:"Right",theme_font_size:"-- Font size --",theme_fontdefault:"-- Font family --",theme_block:"-- Format --",theme_paragraph:"Paragraph",theme_div:"Div",theme_address:"Address",theme_pre:"Preformatted",theme_h1:"Heading 1",theme_h2:"Heading 2",theme_h3:"Heading 3",theme_h4:"Heading 4",theme_h5:"Heading 5",theme_h6:"Heading 6",theme_blockquote:"Blockquote",theme_code:"Code",theme_samp:"Code sample",theme_dt:"Definition term ",theme_dd:"Definition description",theme_colorpicker_title:"Select a color",theme_colorpicker_apply:"Apply",theme_forecolor_desc:"Select text color",theme_backcolor_desc:"Select background color",theme_charmap_title:"Select custom character",theme_charmap_desc:"Insert custom character",theme_visualaid_desc:"Toggle guidelines/invisible elements",insert_anchor_title:"Insert/edit anchor",insert_anchor_name:"Anchor name",theme_anchor_desc:"Insert/edit anchor",theme_insert_link_titlefield:"Title",theme_clipboard_msg:"Copy/Cut/Paste is not available in Mozilla and Firefox.\nDo you want more information about this issue?",theme_path:"Path",cut_desc:"Cut",copy_desc:"Copy",paste_desc:"Paste",link_list:"Link list",image_list:"Image list",browse:"Browse",image_props_desc:"Image properties",newdocument_desc:"New document",class_name:"Class",newdocument:"Are you sure you want clear all contents?",about_title:"About TinyMCE",about:"About",license:"License",plugins:"Plugins",plugin:"Plugin",author:"Author",version:"Version",loaded_plugins:"Loaded plugins",help:"Help",not_set:"-- Not set --",close:"Close",toolbar_focus:"Jump to tool buttons - Alt+Q, Jump to editor - Alt-Z, Jump to element path - Alt-X",invalid_data:"Error: Invalid values entered, these are marked in red.",more_colors:"More colors",color_picker_tab:"Picker",color_picker:"Color picker",web_colors_tab:"Palette",web_colors:"Palette colors",named_colors_tab:"Named",named_colors:"Named colors",color:"Color:",color_name:"Name:",is_email:"The URL you entered seems to be an email address, do you want to add the required mailto: prefix?",is_external:"The URL you entered seems to external link, do you want to add the required http:// prefix?"});