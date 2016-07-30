package com.pump.smartbank.comm;

import android.graphics.Bitmap;

import com.pump.smartbank.util.PictureUtil;

import java.net.Socket;
import java.util.Vector;

/**
 * Created by xu.nan on 2016/7/27.
 */
public class BankDoingComm extends BaseComm {

    private static final short BANK_DOING_REQ = 604;
    private static final short BANK_DOING_RET = 6604;

    private Bitmap bitmap;

    public BankDoingComm(Socket socket,Bitmap bitmap) {
        super(socket, BANK_DOING_REQ, BANK_DOING_RET);
        this.bitmap = bitmap;
    }

    @Override
    protected Vector<Byte> MakePackBody() {
        Vector<Byte> data = new Vector<Byte>();
        data.clear();
        data.addAll(MakeField(PictureUtil.convertIconToString(bitmap)));
        return data;
    }

    @Override
    protected int fetchData(byte[] data){
        int result = bytestous(data);
        switch (result) {
            case 0:
                return 0;
            default:
                message = "未知应答码["+result+"]";
                return -4;
        }
    }

}
