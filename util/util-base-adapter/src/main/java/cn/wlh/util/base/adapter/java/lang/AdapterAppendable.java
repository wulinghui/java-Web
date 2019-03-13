package cn.wlh.util.base.adapter.java.lang;

import java.io.IOException;

/**
 * @author 吴灵辉
 * 有些时候想扩充SB的方法..但是他是final的   不支持.
 * 只能追加..cn.wlh.util.base.adapter.json.AdapterAppendableTest.test0()
 * @see sun是真的贱.. AbstractStringBuilder不是公开的...
 * @param <K>
 */
public class AdapterAppendable<K extends Appendable> implements Appendable {
	protected K sb;
	static final String MESSAGE = "不可能的异常";

	public AdapterAppendable(K sb) {
		this.sb = sb;
	}

	@Override
	public AdapterAppendable<K> append(CharSequence csq) {
		try {
			sb.append(csq);
			return this;
		} catch (IOException e) {
			throw new RuntimeException(MESSAGE);
		}
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) {
		try {
			sb.append(csq, start, end);
			return this;
		} catch (IOException e) {
			throw new RuntimeException(MESSAGE);
		}
	}

	@Override
	public AdapterAppendable<K> append(char c) {
		try {
			sb.append(c);
			return this;
		} catch (IOException e) {
			throw new RuntimeException(MESSAGE);
		}
	}
	public K getSb() {
		return sb;
	}

	public void setSb(K sb) {
		this.sb = sb;
	}
}
