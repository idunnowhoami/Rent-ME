package Utilidades;

import Model.Cliente;
import Model.Veiculo;

public class DataSingleton {

        private Cliente dataCliente;
        private Veiculo dataVeiculo;
        private static DataSingleton instance = null;

        private DataSingleton() {
            // Private constructor to prevent other classes from instantiating
        }

        public static DataSingleton getInstance() {
            if (instance == null) {
                instance = new DataSingleton();
            }
            return instance;
        }

        public Cliente getData() {
            return dataCliente;
        }

        public void setData(Cliente dataCliente) {
            this.dataCliente = dataCliente;
        }
        public Veiculo getDataVeiculo() {
            return dataVeiculo;
    }
        public void setDataVeiculo(Veiculo dataVeiculo) {
            this.dataVeiculo = dataVeiculo;
    }


    }


