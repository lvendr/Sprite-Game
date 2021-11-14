/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import SpriteSong.SpriteColorConverter;
import java.awt.Color;
import javax.faces.component.UIColumn;
import javax.faces.context.FacesContextWrapper;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Song Nguyen Nguyen $ Liam Dickson 
 * Student Number: 040 940 830 & 040 933 739 
 * Assignment: 2 
 * Professor: Todd Kelley 
 * Lab Prof: Todd Kelly Lab#: 301
 * Class: TestColorConverter 
 * Description: Test the color converter method of the JSF pages
 */
public class TestColorConverter {

    /**
     *
     * Test the getAsObject method of the converter
     */
    @Test
    public void testColorConverter1() {
        SpriteColorConverter converter = new SpriteColorConverter();

        Color referenceColor = new Color(79, 41, 86);

        Color colorReturned = (Color) converter.getAsObject(new FacesContextWrapper() {
        }, new UIColumn(), "[79, 41, 86]");

        assertEquals(referenceColor, colorReturned);
    }

    /**
     * 
     * Test the getAsString method of the converter
     */
    @Test
    public void testColorConverter2() {
        SpriteColorConverter converter = new SpriteColorConverter();

        Color colorObject = new Color(79, 41, 86);

        String colorString;

        colorString = converter.getAsString(new FacesContextWrapper() {
        }, new UIColumn(), colorObject);

        assertEquals(colorString, "[79, 41, 86]");
    }
}
