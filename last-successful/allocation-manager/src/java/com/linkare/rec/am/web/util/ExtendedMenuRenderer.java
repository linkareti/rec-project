package com.linkare.rec.am.web.util;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import com.sun.faces.renderkit.html_basic.MenuRenderer;


/**
 * Extended menu renderer which renders the 'optionClasses' attribute above the standard menu
 * renderer. To use it, define it as follows in the render-kit tag of faces-config.xml.
 *
 * <pre>
 * &lt;renderer&gt;
 *     &lt;component-family&gt;javax.faces.SelectOne&lt;/component-family&gt;
 *     &lt;renderer-type&gt;javax.faces.Menu&lt;/renderer-type&gt;
 *     &lt;renderer-class&gt;net.balusc.jsf.renderer.html.ExtendedMenuRenderer&lt;/renderer-class&gt;
 * &lt;/renderer&gt;
 * &lt;renderer&gt;
 *     &lt;component-family&gt;javax.faces.SelectMany&lt;/component-family&gt;
 *     &lt;renderer-type&gt;javax.faces.Menu&lt;/renderer-type&gt;
 *     &lt;renderer-class&gt;net.balusc.jsf.renderer.html.ExtendedMenuRenderer&lt;/renderer-class&gt;
 * &lt;/renderer&gt;
 * </pre>
 *
 * And define the 'optionClasses' attribute as a f:attribute of the h:selectOneMenu or
 * h:selectManyMenu as follows:
 *
 * <pre>
 * &lt;f:attribute name="optionClasses" value="option1,option2,option3" /&gt;
 * </pre>
 *
 * It accepts a comma separated string of CSS class names which are to be applied on the options
 * repeatedly (the same way as you use rowClasses in h:dataTable). The optionClasses will be
 * rendered only if there is no 'disabledClass' or 'enabledClass' being set as an attribute.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/styling-options-in-hselectonemenu.html
 */
public class ExtendedMenuRenderer extends MenuRenderer {

    // Override -----------------------------------------------------------------------------------

    /**
     * @see com.sun.faces.renderkit.html_basic.MenuRenderer#renderOption(
     *      javax.faces.context.FacesContext, javax.faces.component.UIComponent,
     *      javax.faces.convert.Converter, javax.faces.model.SelectItem, java.lang.Object,
     *      java.lang.Object[])
     */
    protected void renderOption(FacesContext context, UIComponent component, Converter converter,
        SelectItem currentItem, Object currentSelections, Object[] submittedValues)
            throws IOException
    {
        // Copied from MenuRenderer#renderOption() (and a bit rewritten, but that's just me) ------

        // Get writer.
        ResponseWriter writer = context.getResponseWriter();
        assert (writer != null);

        // Write 'option' tag.
        writer.writeText("\t", component, null);
        writer.startElement("option", component);

        // Write 'value' attribute.
        String valueString = getFormattedValue(context, component, currentItem.getValue(), converter);
        writer.writeAttribute("value", valueString, "value");

        // Write 'selected' attribute.
        Object valuesArray;
        Object itemValue;
        if (containsaValue(submittedValues)) {
            valuesArray = submittedValues;
            itemValue = valueString;
        } else {
            valuesArray = currentSelections;
            itemValue = currentItem.getValue();
        }
        if (isSelected(context, component,itemValue, valuesArray, converter)) {
            writer.writeAttribute("selected", true, "selected");
        }

        // Write 'disabled' attribute.
        Boolean disabledAttr = (Boolean) component.getAttributes().get("disabled");
        boolean componentDisabled = disabledAttr != null && disabledAttr.booleanValue();
        if (!componentDisabled && currentItem.isDisabled()) {
            writer.writeAttribute("disabled", true, "disabled");
        }

        // Write 'class' attribute.
        String labelClass;
        if (componentDisabled || currentItem.isDisabled()) {
            labelClass = (String) component.getAttributes().get("disabledClass");
        } else {
            labelClass = (String) component.getAttributes().get("enabledClass");
        }

        // Inserted custom code which checks the optionClasses attribute --------------------------

        if (labelClass == null) {
            String optionClasses = (String) component.getAttributes().get("optionClasses");
            if (optionClasses != null) {
                String[] labelClasses = optionClasses.split("\\s*,\\s*");
                String indexKey = component.getClientId(context) + "_currentOptionIndex";
                Integer index = (Integer) component.getAttributes().get(indexKey);
                if (index == null || index == labelClasses.length) {
                    index = 0;
                }
                labelClass = labelClasses[index];
                component.getAttributes().put(indexKey, ++index);
            }
        }

        // The remaining copy of MenuRenderer#renderOption() --------------------------------------

        if (labelClass != null) {
            writer.writeAttribute("class", labelClass, "labelClass");
        }

        // Write option body (the option label).
        if (currentItem.isEscape()) {
            String label = currentItem.getLabel();
            if (label == null) {
                label = valueString;
            }
            writer.writeText(label, component, "label");
        } else {
            writer.write(currentItem.getLabel());
        }

        // Write 'option' end tag.
        writer.endElement("option");
        writer.writeText("\n", component, null);
    }

}
