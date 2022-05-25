package br.poli.ecomp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

//	declaração das variáveis
	int porta;
	InetAddress ipServidor;
	static DatagramSocket socketUDP;
	DatagramPacket pacoteRecebido;
	DatagramPacket enviaPacote;
	private Scanner leitura;
	private static Scanner in;

//	construtor da porta e ip
	public Cliente(String ip, int porta) throws UnknownHostException {
		ipServidor = InetAddress.getByName(ip);
		this.porta = porta;
	}

	/**
	 * função que inicia a consulta ao servidor
	 */
	public void iniciar() throws IOException {

		leitura = new Scanner(System.in);
//		criação de socket UDP
		socketUDP = new DatagramSocket();

		System.out.println("Conectado ao servidor.");
//		enviando o pacote
//		lendo a msg q será enviado ao servidor
		System.out.print("Digite o texto para converter todas as letras em maiúsculas: ");
		String msg = leitura.nextLine();
//		convertendo a msg em bytes
		byte[] enviarMsg = msg.getBytes();
//		declaração do pacote a ser enviado
		enviaPacote = new DatagramPacket(enviarMsg, enviarMsg.length, ipServidor, porta);
//		enviando o datagram
		socketUDP.send(enviaPacote);
//		recebendo o pacote
//		pacote recebido
		pacoteRecebido = new DatagramPacket(new byte[1024], 1024);
//		aguardando respota do servidor
		socketUDP.receive(pacoteRecebido);
//		chegada do datagram, 
		String msgSaida = new String(pacoteRecebido.getData());
		System.out.println("Resposta do servidor: " + msgSaida);
		finalizar();
	}

	/**
	 * função que finaliza a conexão
	 */
	public void finalizar() {
		try {
			socketUDP.close();
			System.out.println("Conexão encerrada.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) throws IOException {

		String ipServ;
		System.out.println("Digite o ip do servidor: ");
		in = new Scanner(System.in);
		ipServ = in.nextLine();

			Cliente cliente = new Cliente(ipServ, 9990);
			cliente.iniciar();

	}
}