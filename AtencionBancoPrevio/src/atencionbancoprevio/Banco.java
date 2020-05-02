/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atencionbancoprevio;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Cristian
 */
public class Banco{
    
    private LinkedList<Caja> cajas; 


    public Banco() {
        cajas = new LinkedList();
    }
    
    public Caja darPrimeraCaja(){
        return this.cajas.getFirst();
    }
    
    public void agregarCaja(int numCaja, String tipoTransaccion, double dineroInicial){
        cajas.add(new Caja(numCaja, tipoTransaccion, dineroInicial));
    }
    
    public boolean recargarCaja(int numCaja, double monto){
        
        for (Caja c : this.cajas) {
            if(c.getIdentificador() == numCaja && c.getTipoTransaccion().equals("Retiro")){
                c.recargarCaja(monto);
                return true;
            }
        }
        return false;
    }
    
    public void eliminarCaja(int numCaja){
        
        if(this.cajas.isEmpty())
            return;
        
        Caja x = cajas.get(numCaja-1);
        
        if(!x.getClientesCola().isEmpty())
            cajas.remove(numCaja-1);
    }
    
    public Caja buscarCaja(int numCaja){
        if(this.cajas.isEmpty())
            return null;
        return this.cajas.get(numCaja-1);
    }
    
    public String verClientes(int numCaja){
    
        for (Caja c : this.cajas) {
            if(c.getIdentificador() == numCaja)
                return c.verClientes();
        }
        return "No hay personas en espera";
    }
    
    public Cliente buscarCliente(int idCliente){
        Cliente cliente;
        
        for (Caja c : cajas) {
            cliente = c.buscarClienteCola(idCliente);
            if (cliente!=null) {
                return cliente;
            }
        }
        return null;
    }
    
    public boolean agregarCliente(int idCliente, int edad, String tipoTransaccion){
        //Buscar las cajas con el tipo de transaccion
        LinkedList<Caja> ca = new LinkedList();
        for (Caja c : this.cajas) {
            if(c.getTipoTransaccion().equals(tipoTransaccion)){
                ca.add(c);
            }
        }
        if(ca.isEmpty())
            return false;
        //Ver cual caja tiene el menor tiempo de transaccion
        Collections.sort(ca);
        ca.getFirst().agregarCliente(idCliente,edad,tipoTransaccion);
        return true;
    }
    
    public boolean despacharCliente(int idCaja, double valorTransaccion){
        
        Caja caja;
        for (Caja c : this.cajas) {
            if (c.getIdentificador()==idCaja) {
                if(c.despacharCliente(valorTransaccion))//si la caja tiene dinero lo despacho
                    return true;
            }
        }
        return false;
    }
    
    public String generarReporte(){
         //TIPO DE TRANSACCION ATENDIDA POR CAJA, MONTO INICIAL DEL DINERO DE CADA CAJA
         //EL MONTO ACTUAL DE DINERO, NUMERO DE CLIENTES ATENDIDOS POR CADA CAJA y NUEMRO DE CLIENTES POR ATENDER
        String rta = "**********Reporte del Banco***********\n";
        for (Caja caja : this.cajas) {
            rta+=">>Id caja: "+caja.getIdentificador()+"\n  Tipo de Transaccion de caja: "+caja.getTipoTransaccion()+
                    "\n  Monto Incial: "+caja.getDineroInicial()+"\n  Monto Actual: "+caja.getDineroCaja()+
                    "\n  Cantidad de clientes atendidos: "+caja.getClientesAtendidos()+
                    "\n  Clientes en cola: "+caja.getCantidadClientes()+"\n";
        }
        return rta;
           
    }
    
    public LinkedList<Caja> getCajas() {
        return cajas;
    }

    public void setCajas(LinkedList<Caja> cajas) {
        this.cajas = cajas;
    }
    
}
