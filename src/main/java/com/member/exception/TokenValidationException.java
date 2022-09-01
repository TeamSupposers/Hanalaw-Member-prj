package com.member.exception;

public class TokenValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 1. �Ű� ������ ���� �⺻ ������
	public TokenValidationException() {

    }

    // 2. ���� �߻� ����(���� �޽���)�� �����ϱ� ���� String Ÿ���� �Ű������� ���� ������
	public TokenValidationException(String message) {
        super(message); // RuntimeException Ŭ������ �����ڸ� ȣ���մϴ�.
    }
}
