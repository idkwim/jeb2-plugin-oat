package com.pnf.OAT;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.pnfsoftware.jeb.core.PluginInformation;
import com.pnfsoftware.jeb.core.properties.IPropertyDefinitionManager;
import com.pnfsoftware.jeb.core.properties.IPropertyManager;
import com.pnfsoftware.jeb.core.units.AbstractUnitIdentifier;
import com.pnfsoftware.jeb.core.units.IBinaryFrames;
import com.pnfsoftware.jeb.core.units.IUnit;
import com.pnfsoftware.jeb.core.units.IUnitProcessor;
import com.pnfsoftware.jeb.util.logging.GlobalLog;
import com.pnfsoftware.jeb.util.logging.ILogger;




public class OATPlugin extends AbstractUnitIdentifier {
    private static final ILogger logger = GlobalLog.getLogger(OATPlugin.class);
    static {
        System.out.println("OATPlugin Loaded");
    }


    public OATPlugin() {
        super("OAT_file", 0);
    }

    @Override
    public PluginInformation getPluginInformation() {
        return new PluginInformation("OAT File Unit", "", "1.0", "PNF Software");
    }

    @Override
    public void initialize(IPropertyDefinitionManager parent, IPropertyManager pm) {
        super.initialize(parent, pm);
    }
    

    @Override
    public boolean identify(byte[] data, IUnit parent) {
        return checkBytes(data, 0, (byte)'o', (byte)'a', (byte)'t', (byte)'\n');
    }
    @Override
    public IUnit prepare(String name, byte[] data, IUnitProcessor unitProcessor, IUnit parent) {
        OATUnit unit = new OATUnit(name, data, unitProcessor, parent, pdm);
        unit.process();
        return unit;
    }

    @Override
    public IUnit reload(IBinaryFrames serializedData, IUnitProcessor unitProcessor, 
            IUnit parent) {
        return null;
    }
    public String readStringFromBytes(byte[] data, int offset) {
        String output = "";
        int index=0;
        while(offset + index < data.length) {
            if(data[offset + index] == 0) {
                break;
            }
            output = output + (char)data[offset + index];
            index++;
        }
        return output;
    }
    private int readLInt(byte[] data, int offset) {
        return ByteBuffer.wrap(new byte[]{data[offset+3], data[offset+2], data[offset+1], data[offset]}).getInt();
    } 
    private short readLShort(byte[] data, int offset) {
        return ByteBuffer.wrap(new byte[]{data[offset+1], data[offset]}).getShort();
    }
} 
