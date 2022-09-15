package sqlmap;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybaticsManager {
	// Mybatis의 SqlSession객체 생성 
	private static SqlSessionFactory instance;
	
	private MybaticsManager() {
		
	}

	/*
	 * 생성자는 보통 public, 여기선 private처리 따라서 외부에서 접근이 안된다.
	 * 웹프로그램 : 웹은 불특정 다수가 접속해서 서비스를 받음
	 * 다수의 인스턴스 생성을 막고 하나의 인스턴스만 생성시켜 처리한다.
	 * 이러한 프로그래밍 기법을 싱글톤패턴기법이라 한다.
	 */
	
	public static SqlSessionFactory getInstance() {
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("sqlmap/sqlMapConfig.xml");
			instance = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) reader.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return instance;
	}
}
