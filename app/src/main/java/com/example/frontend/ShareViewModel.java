package com.example.frontend;



import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {

    private  MutableLiveData<String> liveData=new MutableLiveData<String>();

    public MutableLiveData<String> getLiveData(){
        return liveData;
    }

    public void setLiveData(String str){
        liveData.setValue(str);
    }

}
