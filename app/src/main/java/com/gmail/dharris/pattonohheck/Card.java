package com.gmail.dharris.pattonohheck;

import android.widget.ImageView;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "sortValue" })

public class Card {
    private String resourceName;

    public Card()
    {

    }

    public String getResourceName() {
        return resourceName;
    }

    public Card(String resource)
    {
        resourceName=resource;
    }

    public int getSortValue(Card trump)
    {
        int total=0;
        int cVal=0;
        int dVal=14;
        int sVal=28;
        int hVal=42;
        if (SettingObject.isTrumpRight())
        {
            switch (trump.getResourceName().charAt(0))
            {
                case 'c':
                    dVal=0;
                    sVal=14;
                    hVal=28;
                    cVal=42;
                    break;
                case 'd':
                    cVal=0;
                    hVal=14;
                    sVal=28;
                    dVal=42;
                    break;
                case 'h':
                    cVal=0;
                    dVal=14;
                    sVal=28;
                    hVal=42;
                    break;
                case 's':
                    dVal=0;
                    cVal=14;
                    hVal=28;
                    sVal=42;
                    break;
            }
        }
        else
        {

                switch (trump.getResourceName().charAt(0))
                {
                    case 'c':
                        dVal=42;
                        sVal=28;
                        hVal=14;
                        cVal=0;
                        break;
                    case 'd':
                        cVal=42;
                        hVal=28;
                        sVal=14;
                        dVal=0;
                        break;
                    case 'h':
                        cVal=42;
                        dVal=28;
                        sVal=14;
                        hVal=0;
                        break;
                    case 's':
                        dVal=42;
                        cVal=28;
                        hVal=14;
                        sVal=0;
                        break;
                }
        }
        switch (resourceName.charAt(0))
        {
            case 'c':
                total=total+cVal;
                break;
          case 'd':
              total=total+dVal;
              break;
          case 's':
              total=total+sVal;
              break;
          case 'h':
              total=total+hVal;
              break;
        }
        int baseValue=0;
        int multiplier=1;

        if (SettingObject.isHighToLow())
        {
            baseValue=13;
            multiplier=-1;

        }
        System.out.println(resourceName + " - "+total + " - "+sVal + " - "+hVal + " - "+dVal+" - "+cVal);

        switch (resourceName.charAt(2)) {
            case 'a':
                total=total+baseValue+(13*multiplier);
                break;
            case 'k':
                total=total+baseValue+(12*multiplier);
                break;
            case 'q':
                total=total+baseValue+(11*multiplier);
                break;
            case 'j':
                total=total+baseValue+(10*multiplier);
                break;
            case '1':
                total=total+baseValue+(9*multiplier);
                break;
            case '9':
                total=total+baseValue+(8*multiplier);
                break;
            case '8':
                total=total+baseValue+(7*multiplier);
                break;
            case '7':
                total=total+baseValue+(6*multiplier);
                break;
            case '6':
                total=total+baseValue+(5*multiplier);
                break;
            case '5':
                total=total+baseValue+(4*multiplier);
                break;
            case '4':
                total=total+baseValue+(3*multiplier);
                break;
            case '3':
                total=total+baseValue+(2*multiplier);
                break;
            case '2':
                total=total+baseValue+(1*multiplier);
                break;

        }
            return total;
    }

    @JsonIgnore
    public ImageView imgView;
}
