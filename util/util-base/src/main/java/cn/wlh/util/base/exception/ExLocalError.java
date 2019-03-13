package cn.wlh.util.base.exception;
@Deprecated //用工厂建
public class ExLocalError extends ExAbstractThrowableLocal2<Error> {

	public ExLocalError(int arrayLenth) {
		super(arrayLenth);
	}
}
