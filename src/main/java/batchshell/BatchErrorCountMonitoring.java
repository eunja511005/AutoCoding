package batchshell;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BatchErrorCountMonitoring {

	public static void main(String[] args) throws IOException {
		log.info("Batch_Test Start.");

		Properties properties = PropertiesLoaderUtils.loadAllProperties("application.properties");
		String dbUrl = properties.getProperty("spring.datasource.url");
		String dbUsername = properties.getProperty("spring.datasource.username");
		String dbPassword = properties.getProperty("spring.datasource.password");

		try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
				Statement statement = connection.createStatement()) {
			// 쿼리 실행
			String query = "SELECT COUNT(*) AS error_count FROM ZTHH_ERRORHIST WHERE CREATE_DT >= SYSDATE - (1/24)";
			try (ResultSet resultSet = statement.executeQuery(query)) {
				// 결과 처리
				while (resultSet.next()) {
					// 결과 처리
					int errorCount = resultSet.getInt("error_count");
					if (log.isDebugEnabled()) {
						log.debug("error_count : " + errorCount);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
