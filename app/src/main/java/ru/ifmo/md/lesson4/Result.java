package ru.ifmo.md.lesson4;

public class Result {
	Expression acc;
	int curPos;

	public Result(Expression acc, int curPos) {
		this.acc = acc;
		this.curPos = curPos;
	}

	public Result() {
		acc = null;
		curPos = 0;
	}

}
