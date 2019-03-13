package cn.wlh.util.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class _Cmd {
	public static void main(String[] args) throws IOException {
		Runtime.getRuntime().exec(
				"C:/Windows/System32/cmd.exe /k start E:/酷狗/KGMusic/KuGou.exe"); // 通过cmd窗口执行命令
		Runtime.getRuntime().exec("C:/Windows/System32/cmd.exe /c calc ");// 通过cmd打开计算器
		/*
		 * C:\Users\liqiang>cmd/? 启动 Windows 命令解释器的一个新实例
		 * 
		 * CMD [/A | /U] [/Q] [/D] [/E:ON | /E:OFF] [/F:ON | /F:OFF] [/V:ON |
		 * /V:OFF] [[/S] [/C | /K] string]
		 * 
		 * /C 执行字符串指定的命令然后终止 /K 执行字符串指定的命令但保留 /S 修改 /C 或 /K 之后的字符串处理(见下) /Q 关闭回显
		 * /D 禁止从注册表执行 AutoRun 命令(见下) /A 使向管道或文件的内部命令输出成为 ANSI /U
		 * 使向管道或文件的内部命令输出成为 Unicode /T:fg 设置前台/背景颜色(详细信息见 COLOR /?) /E:ON
		 * 启用命令扩展(见下) /E:OFF 禁用命令扩展(见下) /F:ON 启用文件和目录名完成字符(见下) /F:OFF
		 * 禁用文件和目录名完成字符(见下) /V:ON 使用 ! 作为分隔符启用延迟的环境变量 扩展。例如，/V:ON 会允许 !var! 在执行时
		 * 扩展变量 var。var 语法会在输入时 扩展变量，这与在一个 FOR 循环内不同。 /V:OFF 禁用延迟的环境扩展。
		 * 
		 * 注意，如果字符串加有引号，可以接受用命令分隔符 "&&" 分隔多个命令。另外，由于兼容性 原因，/X 与 /E:ON 相同，/Y 与
		 * /E:OFF 相同，且 /R 与 /C 相同。任何其他开关都将被忽略。
		 * 
		 * 如果指定了 /C 或 /K，则会将该开关之后的 命令行的剩余部分作为一个命令行处理，其中，会使用下列逻辑 处理引号(")字符:
		 * 
		 * 1. 如果符合下列所有条件，则会保留 命令行上的引号字符:
		 * 
		 * - 不带 /S 开关 - 正好两个引号字符
		 */
	}

	/** 多线程的cmd */
	public static class Command extends Thread {
		private java.lang.Process p;
		private InputStream is;
		private OutputStream os;
		private BufferedWriter bw;
		private BufferedReader br;
		private ProcessBuilder pb;
		private InputStream stdErr;

		public Command() {
		}

		// 获取Process的输入，输出流
		public void setCmd(String cmd) {
			try {
				p = Runtime.getRuntime().exec(cmd);
				os = p.getOutputStream();
				is = p.getInputStream();
				stdErr = p.getErrorStream();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		// 向Process输出命令
		public void writeCmd(String cmd) {
			try {
				bw = new BufferedWriter(new OutputStreamWriter(os));
				bw.write(cmd);
				bw.newLine();
				bw.flush();
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 读出Process执行的结果
		public String readCmd() {
			StringBuffer sb = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(is));
			String buffer = null;
			try {
				while ((buffer = br.readLine()) != null) {
					sb.append(buffer + "\n");
				}
//				System.out.println(p.waitFor());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sb.toString();
		}

		// 将命令一股脑塞入list中
		public LinkedList<String> doCmd(LinkedList<String> lists) {
			LinkedList<String> list = new LinkedList<String>();
			for (String s : lists) {
				writeCmd(s);
				list.add(readCmd());
			}
			return list;
		}

		public void run() {
			Command cmd = new Command();
			cmd.setCmd("cmd");
			cmd.writeCmd("start cmd /k ping -a -t 43.248.133.79 -l 1000");
//			System.out.println(cmd.readCmd());
			// LinkedList<string> list = new LinkedList<string>();
			// list.add("dir/b");
			// list = cmd.doCmd(list);
			// for(String s:list){
			// System.out.print(s);
		}

		public static void main(String[] args) {
			Thread thr1 = new Command();
			Thread thr2 = new Command();
			thr1.start();
			thr2.start();
		}
	}

	/**
	 * 
	 * 一个人公司用的小实例
	 * 
	 */

	public static class CmdProcessor {
		private Process mProcess; // the cmd process
		private PrintWriter mWriter; // write cmd streams to process
		private BufferedReader mReader; // log the cmd response
		private volatile boolean mStop; // stop the process
		private LinkedBlockingDeque<String> mCmds; // the cmd container
		private boolean mLoggable = true; // print log
		private boolean mWarnTimeout = false;
		private long mTimeoutMillis = 30 * 1000; // 30s

		protected long mCmdStartMillis; // record the cmd start time
		protected long mProStartMillis; // record the process start time

		public CmdProcessor() {
			this(null);
		}

		public CmdProcessor(Collection<String> cmds) {
			mCmds = new LinkedBlockingDeque<String>();
			if (cmds != null) {
				mCmds.addAll(cmds); // here is your cmds
			}
		}

		private void init() throws IOException {
			mProcess = Runtime.getRuntime().exec("cmd", getEnvs());
			mReader = new BufferedReader(new InputStreamReader(
					mProcess.getInputStream()));
			mWriter = new PrintWriter(new OutputStreamWriter(
					mProcess.getOutputStream()));
			mCmdStartMillis = System.currentTimeMillis();
			mProStartMillis = mCmdStartMillis;

			if (mWarnTimeout) {
				warnTimeout();
			}
		}

		private String[] getEnvs() {
			LinkedList<String> envList = new LinkedList<String>();
			Map<String, String> envMap = System.getenv();
			for (Entry<String, String> env : envMap.entrySet()) {
				if (!env.getKey().contains("=")
						&& !env.getValue().contains("=")) {
					envList.add(env.getKey() + "=" + env.getValue());
				}
			}
			String[] evns = new String[envList.size()];
			return envList.toArray(evns);
		}

		public void addFirst(String cmd) {
			mCmds.addFirst(cmd);
		}

		public void addLast(String cmd) {
			mCmds.addLast(cmd);
		}

		public void add(String cmd) {
			addLast(cmd);
		}

		/**
		 * Print warning if cmd has no response, you can disable it
		 */
		private void warnTimeout() {
			new Thread(new Runnable() {

				@Override
				public void run() {
					while (!mStop) {
						long passedSec = (System.currentTimeMillis() - mCmdStartMillis);
						if (passedSec > mTimeoutMillis) {
							System.out.println("[" + mCmds.peek()
									+ "] cost seconds:" + passedSec / 1000);
						}
						try {
							Thread.sleep(1050);
						} catch (InterruptedException e) {
						}
					}
				}
			}).start();
		}

		public void setLoggable(boolean loggable) {
			mLoggable = loggable;
		}

		public void waitFor() throws InterruptedException, IOException {
			mProcess.waitFor();
			stop();
		}

		public void waitFor(final long timeout, TimeUnit timeUnit)
				throws Exception {
			String versionStr = System.getProperty("java.version");
			float version = Float.valueOf(versionStr.substring(0, 3));
			if (version >= 1.8f) {
				mProcess.waitFor(timeout, timeUnit);
			} else {
				mockupWaitFor(timeout, timeUnit);
			}
			stop();
		}

		/**
		 * If JDK level is lower than 1.8, you can't use method
		 * Process.waitFor(long, TimeUnit). So here mock up a waitFor(long,
		 * TimeUnit) method.
		 * 
		 * @param timeout
		 * @throws Exception
		 */
		private void mockupWaitFor(long timeout, TimeUnit timeUnit)
				throws Exception {
			final long timeMillis = timeUnit.toMillis(timeout);
			final Object tmpLock = new Object();

			new Thread(new Runnable() {

				@Override
				public void run() {
					long start = System.currentTimeMillis();
					while (true) {
						if (System.currentTimeMillis() - start > timeMillis) {
							synchronized (tmpLock) {
								tmpLock.notify();
							}
							break;
						}

						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();

			synchronized (tmpLock) {
				tmpLock.wait();
			}
		}

		private void executeCmd(String cmd) {
			if (!mStop) {
				mWriter.println(cmd);
				mWriter.flush();
				mCmdStartMillis = System.currentTimeMillis();
			}
		}

		public void start() throws Exception {
			init();

			System.out.println();

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						processCmds();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}

		public void stop() throws IOException {
			mStop = true;
			mProcess.destroy();
			mReader.close();
			mWriter.close();
		}

		public boolean isStopped() {
			return mStop;
		}

		private void processCmds() throws Exception {
			boolean firstReply = true;
			StringBuffer buffer = new StringBuffer();
			char[] chars = new char[1024];
			while (!mStop) {
				if (mReader.ready()) {
					int len = mReader.read(chars);
					buffer.append(chars, 0, len);
					if (mLoggable) {
						System.out.print(new String(chars, 0, len));
					}
				} else {
					String log = buffer.toString();
					boolean isReply = isCmdReply(mCmds.peek(), log);
					if (isReply) {
						buffer.delete(0, buffer.length());

						if (firstReply) {
							firstReply = false;
						} else {
							onCmdReply(mCmds.poll(), log); // on cmd reply
						}

						if (mCmds.peek() == null) { // process next cmd
							onFinish();
						} else {
							String nextCmd = mCmds.peek();
							preExecuteCmd(nextCmd);
							executeCmd(nextCmd);
						}
					}
				}
			}
		}

		// reply???
		protected boolean isCmdReply(String cmd, String log) throws Exception {
			String userDir = System.getProperty("user.dir");
			return log.contains(userDir);
		}

		// on cmd reply
		protected void onCmdReply(String cmd, String reply) throws Exception {

		}

		// pre-execute cmd
		protected void preExecuteCmd(String cmd) throws Exception {

		}

		// all the cmds has been executed
		protected void onFinish() throws Exception {
			stop();

			if (mLoggable) {
				long costSec = (System.currentTimeMillis() - mProStartMillis) / 1000;
				System.out.println("\nCost time: " + costSec);
			}
		}

		public static void main(String[] args) throws Exception {
			CmdProcessor pro = new CmdProcessor();
			pro.addFirst("dir");
			pro.start();

			List<String> cmds = new LinkedList<String>();
			cmds.add("dir /a");
			CmdProcessor cd = new CmdProcessor(cmds);
			cd.start();
		}
	}

	/**
	 * 获取cmd命令 try { Process pro = Runtime.getRuntime().exec("cmd /ccalc");
	 * //添加要进行的命令，"cmd  /ccalc"中calc代表要执行打开计算器，如何设置关机请自己查找cmd命令 BufferedReader
	 * br = new BufferedReader(new InputStreamReader(pro .getInputStream()));
	 * //虽然cmd命令可以直接输出，但是通过IO流技术可以保证对数据进行一个缓冲。 String msg = null; while ((msg =
	 * br.readLine()) != null) { System.out.println(msg); } } catch (IOException
	 * exception) { } cmd /c dir 是执行完dir命令后关闭命令窗口cmd /k dir 是执行完dir命令后不关闭命令窗口cmd
	 * /c start dir 会打开一个新窗口后执行dir命令，原窗口会关闭cmd /k start dir
	 * 会打开一个新窗口后执行dir命令，原窗口不会关闭cmd /? 查看帮助信息
	 */
	/*
	 * -- 获得错误.. InputStreamReader isr = new InputStreamReader(process
	 * .getErrorStream()); // 读取ErrorStream很关键，解决了挂起的问题 _File 的 oracle exp命令
	 * BufferedReader br = new BufferedReader(new
	 * InputStreamReader(process.getErrorStream())); BufferedReader in = new
	 * BufferedReader(new InputStreamReader(process.getInputStream()));
	 */

	public static String impFile(Map<String, Object> map, Process process) {
		String info = "";
		String filepath = ""; // StringUtil.formatDbColumn(map.get("FILEPATH"));
		String[] cmds = new String[3];
		String commandBuf = "imp pip_jk/pip@orcl fromuser=pip_jk touser=dms file="
				+ filepath + " ignore=y";
		cmds[0] = "cmd";
		cmds[1] = "/C";
		cmds[2] = commandBuf.toString();
		// Process process = null;
//		try {
//
//			process = Runtime.getRuntime().exec(cmds);
//		} catch (IOException e) {
//			e.printStackTrace();
//			info = "导入出错";
//		}
		boolean shouldClose = false;
		try {
			InputStreamReader isr = new InputStreamReader(
					process.getErrorStream());
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				if (line.indexOf("????") != -1) {
					shouldClose = true;
					break;
				}
			}
		} catch (IOException ioe) {
			shouldClose = true;
			info = "导入出错";
		}
		if (shouldClose)
			process.destroy();
		int exitVal;
		try {
			exitVal = process.waitFor();
			System.out.print(exitVal);
		} catch (InterruptedException e) {
			e.printStackTrace();
			info = "导入出错";
		}
		return info;
	}
}
