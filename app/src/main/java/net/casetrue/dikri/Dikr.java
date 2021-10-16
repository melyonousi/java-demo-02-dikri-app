package net.casetrue.dikri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Dikr
{
    /*attributs*/
    private String txtDikr;
    private String nbCount;

    /**
     * this is a getter return TextDikr value
     *
     * @return
     */
    public String getTxtDikr()
    {
        return txtDikr;
    }

    /**
     * this is a setter to set TextDikr value
     *
     * @param txtDikr
     */
    public void setTxtDikr(String txtDikr)
    {
        this.txtDikr = txtDikr;
    }

    /**
     * this is a getter return NbCount value
     *
     * @return
     */
    public String getNbCount()
    {
        return nbCount;
    }

    /**
     * this is a setter to set NbCount value
     *
     * @param nbCount
     */
    public void setNbCount(String nbCount)
    {
        this.nbCount = nbCount;
    }

    /**
     * this is a method to change NbCount value from onClickListener
     *
     * @param btnCount
     */
    public void changeBtnCount(String btnCount)
    {
        this.nbCount = btnCount;
    }

    /**
     * this is a method get a value from file exterieu and return a list dikr
     *
     * @param inputStream
     * @return
     */
    public static ArrayList<Dikr> open(InputStream inputStream)
    {
        String allText;
        ArrayList<Dikr> dikris = new ArrayList<>();
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            allText = bufferedReader.readLine();

            while (allText != null)
            {
                Dikr dikr = new Dikr();
                dikr.setTxtDikr(allText.split("/")[0]);
                dikr.setNbCount(allText.split("/")[1]);

                dikris.add(dikr);
                allText = bufferedReader.readLine();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return dikris;
    }
}
