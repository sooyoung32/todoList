package kr.co.kware.common.dbcommon;

public enum DeletionStatus {
	
	DELETED(0), PRESENT(1);
	
	private final int value;

	private DeletionStatus(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
		
	public static DeletionStatus valueOf(int value) {
		switch (value) {
		case 0:
			return DeletionStatus.DELETED;
		case 1:
			return DeletionStatus.PRESENT; 
		default:
			throw new AssertionError("Unknown DeletionStatus : " + value); 
		}
	}
	
	public static void main(String[] args) {                                                                                                      
		
		System.out.println(DeletionStatus.DELETED.value);
		System.out.println(DeletionStatus.PRESENT.value);
		
		
	}
	
	
}
