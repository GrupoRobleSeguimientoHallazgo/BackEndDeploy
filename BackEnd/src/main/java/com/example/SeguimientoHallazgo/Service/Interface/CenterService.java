package com.example.SeguimientoHallazgo.Service.Interface;

import com.example.SeguimientoHallazgo.Domain.Center;

import java.util.List;

public interface CenterService {

    List<Center> listAllCenter();
    Center createCenter(Center pNewCenter);
    Center getCenterInfo(int pCenterId);
    Center updateCenter(int pCenterId, Center pNewCenter);
}
