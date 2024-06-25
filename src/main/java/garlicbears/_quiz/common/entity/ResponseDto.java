package garlicbears._quiz.common.entity;

public class ResponseDto<T> {
	private int status;
	private String code;

	public ResponseDto(int status, String code) {
		this.status = status;
		this.code = code;
	}

  public ResponseDto(String code) {
      this.code = code;
  }

  public int getStatus(){
      return status;
  }

	public String getCode() {
		return code;
	}
  
  public static <T> ResponseDto<T> success(){
      return new ResponseDto<>("SUCCESS");
  }
}
