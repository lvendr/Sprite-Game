/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpriteSong;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Song Nguyen Nguyen $ Liam Dickson
 * Student Number: 040 940 830 & 040 933 739
 * Assignment: 2
 * Professor: Todd Kelley
 * Lab Prof: Todd Kelly
 * Lab#: 301
 * Class: SpriteColorConverter
 * Methods: getAsObject(FacesContext context, UIComponent component, String colorValue),
 *          getAsString(FacesContext context, UIComponent component, Object colorObject)
 * Description: Convert color code from Color object, and convert String back to Color object
 */
@FacesConverter("ColorConverter")
public class SpriteColorConverter implements Converter {

    /**
     * 
     * @param context
     * @param component
     * @param colorValue
     * @return Color Object as the Decimal RGB color code
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String colorValue) {
        List<String> colors = Arrays.asList(colorValue.replaceAll("\\[|\\]", "").split(",")); // remove all of [ ] brackets, and get the color codes by spliting commas
        
        return new Color(Integer.parseInt(colors.get(0).trim()), //red
                Integer.parseInt(colors.get(1).trim()), //green
                Integer.parseInt(colors.get(2).trim())); //blue
    }

    /**
     * 
     * @param context
     * @param component
     * @param colorObject
     * @return String as the Decimal RGB color code
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object colorObject) {
        int red = ((Color) colorObject).getRed();
        int green = ((Color) colorObject).getGreen();
        int blue = ((Color) colorObject).getBlue();

        return String.format("[%0" + String.valueOf(red).length() + "d, %0" + String.valueOf(green).length() + "d, %0" + String.valueOf(blue).length() + "d]",
                red,
                green,
                blue);
    }
}
