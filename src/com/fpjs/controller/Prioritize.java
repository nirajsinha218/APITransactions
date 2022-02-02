package com.fpjs.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fpjs.app.Application;
import com.fpjs.model.Transaction;

public class Prioritize {

	public static List<Transaction> prioritize(List<Transaction> txList, int totalTime) {
		List<Transaction> transactionList = new ArrayList<Transaction>();
		int n = txList.size();
		BigDecimal dp[][] = new BigDecimal[n + 1][totalTime + 1];

		for (int i = 0; i <= totalTime; i++) {
			dp[0][i] = new BigDecimal(0);
		}

		for (int i = 0; i <= n; i++) {
			dp[i][0] = new BigDecimal(0);
		}

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= totalTime; j++) {
				Transaction tx = txList.get(i - 1);
				int delay = Application.latencyMap.get(tx.getBankCountryCode());
				BigDecimal amt = tx.getAmount();
				if (delay > j)
					dp[i][j] = dp[i - 1][j];
				else {
					BigDecimal temp = amt.add(dp[i - 1][j - delay]);
					if (temp.compareTo(dp[i - 1][j]) >= 0) {
						dp[i][j] = temp;
					} else {
						dp[i][j] = dp[i - 1][j];
					}
				}
			}
		}

		BigDecimal res = dp[n][totalTime];

		int w = totalTime;
		for (int i = n; i > 0 && res.compareTo(new BigDecimal(0)) > 0; i--) {
			if (dp[i - 1][w].compareTo(res) == 0)
				continue;
			else {
				Transaction tx = txList.get(i - 1);
				transactionList.add(tx);
				int delay = Application.latencyMap.get(tx.getBankCountryCode());
				BigDecimal amt = tx.getAmount();
				res = res.subtract(amt);
				w = w - delay;
			}
		}

		return transactionList;
	}
}
