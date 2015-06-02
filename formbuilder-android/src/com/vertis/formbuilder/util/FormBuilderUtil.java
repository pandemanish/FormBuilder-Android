package com.vertis.formbuilder.util;

import android.content.Context;
import android.graphics.Typeface;

import com.vertis.formbuilder.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Util Class helps user to set the Font for user
 * Created by manish on 20/05/15.
 */
public class FormBuilderUtil {

    public Typeface getFontFromRes( Context context)
    {
        Typeface tf = null;
        InputStream is ;
        try {
            is = context.getResources().openRawResource(R.raw.roboto);
            String outPath = context.getCacheDir() + "/tmp" + System.currentTimeMillis() + ".raw";
            byte[] buffer = new byte[is.available()];
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outPath));
            int l ;
            while ((l = is.read(buffer)) > 0)
                bos.write(buffer, 0, l);
            bos.close();
            tf = Typeface.createFromFile(outPath);
            new File(outPath).delete();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return tf;
    }
}
