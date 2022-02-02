package com.fpjs.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fpjs.controller.Prioritize;
import com.fpjs.model.Transaction;

public class Application {

	public static List<Transaction> txList = new ArrayList<Transaction>();
	public static Map<String, Integer> latencyMap = new HashMap<String, Integer>();
	public static Set<Transaction> hs = new HashSet<Transaction>();
	private static final String COUNTRY_CODE = "bank_country_code";
	private static final String TRANSACTION_FILE_PATH = "../FingerprintJS/transactions.csv";
	private static final String BANK_CODE = "bank_code";
	private static final String LATENCY_FILE_PATH = "../FingerprintJS/api_latencies.csv";

	private static void readTransactionsFile() {
		String line = "";
		String splitBy = ",";
		try {
			BufferedReader br = new BufferedReader(new FileReader(TRANSACTION_FILE_PATH));
			while ((line = br.readLine()) != null) {
				if (line.contains(COUNTRY_CODE)) {
					continue;
				}
				String[] transaction = line.split(splitBy);
				Transaction tx = new Transaction(transaction[0].trim(),
						BigDecimal.valueOf(Double.parseDouble(transaction[1].trim())), transaction[2].trim());
				txList.add(tx);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readLatencyFile() {
		String line = "";
		String splitBy = ",";
		try {
			BufferedReader br = new BufferedReader(new FileReader(LATENCY_FILE_PATH));
			while ((line = br.readLine()) != null) {
				if (line.contains(BANK_CODE)) {
					continue;
				}
				String[] code = line.split(splitBy);
				latencyMap.put(code[0].trim(), Integer.parseInt(code[1].trim()));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static BigDecimal calculateMaxUSD(List<Transaction> transactionList) {
		BigDecimal maxUSD = new BigDecimal(0);
		for (Transaction tx : transactionList) {
			maxUSD = maxUSD.add(tx.getAmount());
		}

		return maxUSD;
	}

	public static void main(String[] args) throws Exception {
		readTransactionsFile();
		readLatencyFile();
		System.out.println(calculateMaxUSD(Prioritize.prioritize(txList, 50)));
		System.out.println(calculateMaxUSD(Prioritize.prioritize(txList, 60)));
		System.out.println(calculateMaxUSD(Prioritize.prioritize(txList, 90)));
		System.out.println(calculateMaxUSD(Prioritize.prioritize(txList, 1000)));
	}

}
