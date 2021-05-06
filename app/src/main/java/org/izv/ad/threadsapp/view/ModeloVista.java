package org.izv.ad.threadsapp.view;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import org.izv.ad.threadsapp.model.Repositorio;

public class ModeloVista extends ViewModel {

    private Repositorio repositorio;

    public ModeloVista() {
        repositorio = new Repositorio();
    }

    public LiveData<String> getResult() {
        return repositorio.getResult();
    }
}