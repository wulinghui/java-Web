package cn.wlh.util.extend.complier;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import cn.wlh.util.base.Print;
import cn.wlh.util.base._Complier;
import cn.wlh.util.base._File;
import cn.wlh.util.base._File.HandlerFile;
import cn.wlh.util.base._Reader;
import cn.wlh.util.base._Serializt;
import cn.wlh.util.base._Writer;
import cn.wlh.util.base.interfaces.Seri_Clone;
import cn.wlh.util.extend.Conf;
import cn.wlh.util.extend.complier.java.JavaFile;
import cn.wlh.util.extend.complier.java.NewJavaFile;
import cn.wlh.util.extend.database.file._FileBase;

/**
 * 该类是对一个文件的操作
 * 
 * @author wlh 对比之前的版本:该处的输出只能对应,一个..当一个文件要拆分成多个不同的文件且写入不同的文件时.
 *         我们这里的办法是:先把实体类存储到路径中,之后再遍历写入.
 */
public abstract class  ComplierClient implements HandlerFile<IOException> {
	static Print log = Print.newInstance( ComplierClient.class );
	
	public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, IOException, Throwable {
		main( new File("C:\\Users\\wlh\\eclipse-workspace\\util\\util-extend\\src\\main\\java\\util\\extend\\complier\\java\\a") ,
			new ComplierFileWrap[]{
			new ComplierFileWrap<JavaFile>( 
					new NewJavaFile(new String[]{"TODO S-",";",";"}) 
					)
		});
	}
	
	public static void main( File complierDirectory , ComplierFileWrap<?>[] coms) throws IOException, ClassNotFoundException, Throwable, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		if( !complierDirectory.isDirectory() || coms.length == 0 ) return;
		//取消缓存.因为源文件写的会重写.只要不减少文件的输出就没影响..
		//但是序列化的内容,是Set,这个缓存是致命..所以我们这里先把文件都删除,再写入
		String forFile = _File.joinPath(_File.WOKE_PATH , STORE_PATH );
		File fi = new File(forFile);
		_File.delete(fi);
		
		_File.forFile( complierDirectory  , new ComplierClient( coms ){} );
		
		_File.forFile( fi , writeFile);//操作序列化的文件...
		log.log("================= ok ============");
	}
	
	
	
	
	
 static final String STORE_PATH = Conf.config.complierClient_STORE_PATH();
 /** 写入指定的文件中*/
	static HandlerFile<IOException> writeFile = new HandlerFile<IOException>() {
		@Override
		public void handler(File file) throws IOException {
			if (file.isDirectory())
				return;
			try { // 序列化
				EntityFile entity = _Serializt.deserializeObj(file);
				System.out.println("entity=" + entity);
				File f = entity.getStorePath();// 获得路径
				_File.createFile(f);// 创建文件
				_Writer.oneWrite(f, false, entity.toString());// 写入
				// 这里需要编译了
				if (entity instanceof JavaFile) { 
					if (_Complier.complier(entity.toString(), ((JavaFile) entity).className))
						System.out.println("_Complier.complier === ok ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	final ComplierFileWrap<?>[] coms;// 一个文件可以有多种实体类操作,命令模式..

	 ComplierClient(ComplierFileWrap<?>[] coms) {
		this.coms = coms;
	}

	@Override
	public void handler(File file) throws IOException {
		// 1.先把文件全部读入jvm中--尽量不要操作sb.--贯穿上下文..
				System.out.println("file="+file);
				if (!file.isFile()) {
					System.out.println( "!file.isFile()" + !file.isFile() );
					return;
				}
				StringBuffer sb;
					sb = new StringBuffer().append(_Reader.notOutOfMemory(file));
					for (ComplierFileWrap complierFile0 : coms) {
						complierFile0.handle(file, sb);
					}// 最写入指定文件,拆分多次. 子类处理.
				
	}

	// TODO 装饰模式加包装类
	public static class ComplierFileWrap<Entity extends EntityFile> {
		ComplierFile<Entity> complier;
		/**
		 * @param complier
		 *            处理的类,--装饰模式.
		 */
		public ComplierFileWrap(ComplierFile<Entity> complier) {
			this.complier = complier;
		}

		public void handle(File file, StringBuffer sb) {
			complier.handle(file, sb);
		}
	}

	/** 基本的里面,第一次一定要序列化 ,这样零件里面就可以反序列化了. */
	public static interface ComplierFile<Entity extends EntityFile> {
		Entity handle(File file, StringBuffer sb);
	}

	public static abstract class ComplierFileOfPart<Entity extends EntityFile> implements ComplierFile<Entity> {
		ComplierFile<Entity> part;// 零件

		public ComplierFileOfPart(ComplierFile<Entity> part) {
			super();
			this.part = part;
		}

		public ComplierFile<Entity> getPart() {
			return part;
		}
	}

	/**
	 * @author wlh 一个实体类就是一张表,一个id一条数据.
	 */
	@SuppressWarnings("serial")
	public static class EntityFile implements Seri_Clone {
		String storePath;// 要写入的路径,写入指定文件路径
		_FileBase<EntityFile> filebase; // 序列化的路径.   数据库.
		String id;

		protected EntityFile(String id, String storePath) {
			this.id = id;
			this.storePath = storePath;
			this.filebase = new _FileBase(this.getClass().getSimpleName(), ComplierClient.STORE_PATH , this.getClass()); 
		}

		public File getStorePath() {
			return new File(storePath);
		}

		public EntityFile getBean() throws Exception {
			return filebase.getBean(id);
		};

		public void storeBean() throws IOException {
			String path = filebase.getAbsolutePath(id);// 获得路径
			_File.createFile(new File(path));// 创建文件
			filebase.storeBean(this, id);// 存储this
		}
	}
}
