//package com.xtremelabs.robolectric.shadows;
//
//import com.xtremelabs.robolectric.internal.Implementation;
//import com.xtremelabs.robolectric.internal.Implements;
//import com.xtremelabs.robolectric.internal.RealObject;
//
//@Implements(NdefMessage.class)
//public class ShadowNdefMessage {
//    @RealObject
//    private NdefMessage realNdefMessage;
//
//    private NdefRecord[] records;
//
//    public void __constructor__(NdefRecord[] records) {
//        this.records = records;
//    }
//
//    @Implementation
//    public NdefRecord[] getRecords() {
//        return records;
//    }
//}
//
