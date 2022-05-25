package br.poli.ecomp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Servidor {

//	declara��o das vari�veis
	DatagramSocket socketUDP;
	DatagramPacket msgEntrada;
	DatagramPacket msgSaida;
	int porta;

//	construtor da porta
	public Servidor(int porta) {
		this.porta = porta;
	}

	/**
	 * fun��o que inicia o servidor
	 */
	public void iniciar() {
		try {
//			cria��o do socket tipo UDP
			socketUDP = new DatagramSocket(porta);
			System.out.println("Iniciando Servidor UDP...");
			System.out.println("IP do servidor: " + InetAddress.getLocalHost().getHostAddress());
			System.out.println("Porta: " + porta);
			System.out.println("Aguardando cliente...");

			while (true) {
//				recebendo o pacote
//				declara��o do pacote recebido
				msgEntrada = new DatagramPacket(new byte[2048], 2048);
//				recebe o pacote
				socketUDP.receive(msgEntrada);
				System.out.println("Lendo o pacote...");
//				recebendo o vetor de bytes e convertendo para String
				String msg = new String(msgEntrada.getData());
				System.out.println("Solicita��o recebida: " + msg);
				System.out.println("Convertendo texto...");

				String msgEnviada = msg.toUpperCase();

				System.out.println("Solicita��o enviada: " + msgEnviada);
//				enviando o pacote
				byte enviarMsg[] = new byte[1024];
//				convertendo a String em valor de bytes
				enviarMsg = msgEnviada.getBytes();
//				datagram que cont�m a mensagem
				msgSaida = new DatagramPacket(enviarMsg, enviarMsg.length, msgEntrada.getAddress(),	msgEntrada.getPort());
//				pacote enviado para o cliente
				socketUDP.send(msgSaida);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * fun��o que finaliza a conex�o
	 */
	public void finalizar() {
		try {
			socketUDP.close();
			System.out.println("Conex�o encerrada.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws UnknownHostException {

		Servidor servidor = new Servidor(9990);
		servidor.iniciar();

	}
}