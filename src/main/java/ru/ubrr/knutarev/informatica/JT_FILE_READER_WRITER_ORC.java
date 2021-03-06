package ru.ubrr.knutarev.informatica;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.security.MessageDigest;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class JT_FILE_READER_WRITER_ORC {

    static String id = "1";
    static BigDecimal date_time = new BigDecimal("1636551505361000000");
    static String file_path = "/audio_file_test/15_05_11_SSR_50996_83E73535A56D1F4CBC8F47EB685C6808.wav";//"/audio_file_test/100mb.001_";
    static long md_load_id = 0;
    static BigDecimal md_ins_ts =  new BigDecimal("1636551505361000000");
    static BigDecimal md_upd_ts =  new BigDecimal("1636551505361000000");
    static String md_rec_cd = "I";
    static String md_src_cd = "CRM";
    static BigDecimal md_date = new BigDecimal("1636551505361000000");
    static String environment_dwh = "rdwh";
    static String environment_hdfs = "hdfs_test";
    static String environment_hive = "hive_test";
    static String config_jt = "{\"data\":[{\"environment\":\"rdwh\",\"accessFromJava\":{\"host\":\"jdbc:oracle:thin:@rdwh.dbs.ubrr.ru:1521:rdwh\",\"login\":\"ADMDWH\",\"password\":\"ADMDWH1\"}},{\"environment\":\"pdwh\",\"accessFromJava\":{\"host\":\"jdbc:oracle:thin:@pdwh.dbs.ubrr.ru:1521:pdwh\",\"login\":\"\",\"password\":\"\"}},{\"environment\":\"hdfs_test\",\"accessFromJava\":{\"host\":\"hdfs://hdp-main1b.grip.ubrr.ru:8020\",\"login\":\"hive\",\"password\":\"\"}},{\"environment\":\"hdfs_prod\",\"accessFromJava\":{\"host\":\"hdfs://hdp-main1a.grip.ubrr.ru:8020\",\"login\":\"hive\",\"password\":\"\"}},{\"environment\":\"hive_test\",\"accessFromJava\":{\"host\":\"jdbc:hive2://hdp-main1b.grip.ubrr.ru:10000/default;UseNativeQuery=1;\",\"login\":\"hive\",\"password\":\"\"}},{\"environment\":\"hive_prod\",\"accessFromJava\":{\"host\":\"jdbc:hive2://hdp-main1a.grip.ubrr.ru:10000/default;UseNativeQuery=1;\",\"login\":\"hive\",\"password\":\"\"}}]}";
    static String table = "ods_crm.calls";

    static Object lock = new Object();
    static org.apache.hive.jdbc.HiveDriver jdbcHiveDriver = new org.apache.hive.jdbc.HiveDriver();
    static int countRows;
    static int countLoadFile;
    static long load_id;
    static String environment_dwh_in;
    static String environment_hdfs_in;
    static String environment_hive_in;
    static ArrayList<HashMap> configList = new ArrayList<HashMap>();
    static String table_in;
    static long maxlength = 24575250;//Integer.MAX_VALUE (?????? ???????????????? ?????????? ?????????????? ??????????????????????) ?????? 32767000 (?????? insert ?????????? jdbc)
    static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    //static SimpleDateFormat sdfTimeshtamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    static Instant epoch = OffsetDateTime.of(1970,1,1,0,0,0,0,ZoneOffset.UTC).toInstant();

    static ArrayList configListAdd (String config_jt)  {
        try {
            JSONObject jsonObject = new JSONObject(config_jt);
            JSONArray configs = jsonObject.getJSONArray("data");
            for (int i = 0; i < configs.length(); i++) {
                JSONObject config = configs.getJSONObject(i);
                String environment = config.getString("environment");
                JSONObject accessFromJava = config.getJSONObject("accessFromJava");
                String host = accessFromJava.getString("host");
                String login = accessFromJava.getString("login");
                String password = accessFromJava.getString("password");
                HashMap<String, String> configeEnvironment = new HashMap<>();
                configeEnvironment.put("environment", environment);
                configeEnvironment.put("host", host);
                configeEnvironment.put("login", login);
                configeEnvironment.put("password", password);
                configList.add(configeEnvironment);
            }
            return configList;
        } catch (Exception e) {
            writeLogProfile("???????????? ???????????? json-?????????????? ?? ?????????????????? JT: " + e, "ERROR", "CRITICAL");
            return configList;
        }
    }

    static void writeLogProfile (String message, String message_type_cd, String severity_cd)  {
        String sql = "call dwh.md_etl_utils.md_profile (?,?,?,?,?)";
        try (
                Connection connection = DriverManager.getConnection(
                        configList.stream().filter((s) -> s.get("environment").equals(environment_dwh_in) ).findFirst().get().get("host").toString()
                        , configList.stream().filter((s) -> s.get("environment").equals(environment_dwh_in) ).findFirst().get().get("login").toString()
                        , configList.stream().filter((s) -> s.get("environment").equals(environment_dwh_in) ).findFirst().get().get("password").toString()
                );
                CallableStatement call = connection.prepareCall(sql)
        ) {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            call.setLong(1, load_id);
            call.setString(2, "JT_FILE_READER_WRITER");
            call.setString(3, message);
            call.setString(4, message_type_cd);
            call.setString(5, severity_cd);
            call.executeQuery();
        } catch (Exception e) {
            //logInfo("JT_FILE_READER_WRITER. ???????????? ?? ?????????????? ?????????? call dwh.md_etl_utils.md_profile.");
            e.printStackTrace();
        }
    }

    static void createTempTableHive ()   {
        try (
                Connection connection = DriverManager.getConnection(
                        configList.stream().filter((s) -> s.get("environment").equals(environment_hive_in) ).findFirst().get().get("host").toString()
                        , configList.stream().filter((s) -> s.get("environment").equals(environment_hive_in) ).findFirst().get().get("login").toString()
                        , configList.stream().filter((s) -> s.get("environment").equals(environment_hive_in) ).findFirst().get().get("password").toString()
                );
                PreparedStatement pstmD = connection.prepareStatement("DROP TABLE " + table_in + "_temp PURGE");
                PreparedStatement pstmC = connection.prepareStatement(
                        "CREATE TABLE " + table_in + "_temp"
                                + "("
                                + "  id           STRING"
                                + ", date_time    TIMESTAMP"
                                + ", file_name    STRING"
                                + ", file_path    STRING"
                                + ", file_binary  BINARY"
                                + ", file_MD5     STRING"
                                + ", md_load_id   BIGINT"
                                + ", md_ins_ts    TIMESTAMP"
                                + ", md_upd_ts    TIMESTAMP"
                                + ", md_rec_cd    CHAR(1)"
                                + ", md_src_cd    CHAR(3)"
                                + ", md_date      DATE"
                                + " )"
                )
        ) {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            pstmD.execute();
            pstmC.execute();
        } catch (Exception e) {
            writeLogProfile("???????????? ???????????????? ?????????????????? ??????????????: "  + table_in + "_temp: " + e, "ERROR", "CRITICAL");
        }
    }

    static void loadFile (final String id, final String file_path, final BigDecimal date_time, final long md_load_id, BigDecimal md_ins_ts, BigDecimal md_upd_ts, String md_rec_cd, String md_src_cd, BigDecimal md_date)   {
        File fileIn = new File(file_path);
        Path hdfsReadPath = new Path(fileIn.toString());
        try (
                FileSystem fileSystem = FileSystem.get(
                        URI.create(configList.stream().filter((s) -> s.get("environment").equals(environment_hdfs_in) ).findFirst().get().get("host").toString())
                        , new Configuration()
                        , configList.stream().filter((s) -> s.get("environment").equals(environment_hdfs_in) ).findFirst().get().get("login").toString()
                );
                InputStream is = fileSystem.open(hdfsReadPath)
        ) {
            long length = fileSystem.getContentSummary(hdfsReadPath).getLength();
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (length > maxlength) {
                throw new Exception("?????????????????? ?????????????????????? ???? ???????????? ??????????: " + file_path + " " + length + " ?????????????????? " + maxlength);
            }
            byte[] bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            try {
                while (offset < bytes.length && (numRead = is.read(bytes, offset, Math.min(bytes.length - offset, 512 * 1024))) >= 0) {
                    offset += numRead;
                }
            } catch (Exception e) {
                writeLogProfile("???????????? ???????????? ??????????: " + e, "ERROR", "LOW");
            }
            if (offset < bytes.length) {
                throw new Exception("???????? ???? ?????????????????? " + file_path);
            } else {
                digest.update(bytes,0,bytes.length);
                String md5 = new BigInteger(1,digest.digest()).toString(16);
                insertDataToTempTableHive (id, date_time, fileIn.getName(), file_path, bytes, md5, md_load_id, md_ins_ts, md_upd_ts, md_rec_cd, md_src_cd, md_date);
            }
        } catch (Exception e) {
            writeLogProfile("???????????? ???????????? loadFile: " + e.toString(), "ERROR", "LOW");
        }
    }

    static void insertDataToTempTableHive (String id, BigDecimal date_time, String file_name, String file_path, byte[] bytes, String file_md5, long md_load_id, BigDecimal md_ins_ts, BigDecimal md_upd_ts, String md_rec_cd, String md_src_cd, BigDecimal md_date)   {
        System.setProperty("HADOOP_USER_NAME", configList.stream().filter((s) -> s.get("environment").equals(environment_hdfs_in) ).findFirst().get().get("login").toString());
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", configList.stream().filter((s) -> s.get("environment").equals(environment_hdfs_in) ).findFirst().get().get("host").toString());
        TypeDescription schema = TypeDescription.createStruct().addField("id", TypeDescription.createString()).addField("date_time", TypeDescription.createString()).addField("file_name", TypeDescription.createString()).addField("file_path", TypeDescription.createString()).addField("binary_value", TypeDescription.createBinary()).addField("file_MD5", TypeDescription.createString()).addField("md_load_id", TypeDescription.createLong()).addField("md_ins_ts", TypeDescription.createString()).addField("md_upd_ts", TypeDescription.createString()).addField("md_rec_cd", TypeDescription.createString()).addField("md_src_cd", TypeDescription.createString()).addField("md_date", TypeDescription.createString());
        try {
            Writer writer = OrcFile.createWriter(new Path("/apps/hive/warehouse/" + table_in.substring(0,table_in.indexOf('.'))+".db/" + table_in.substring(table_in.indexOf('.')+1)+"_temp/" + UUID.randomUUID().toString().getBytes()+".orc"), OrcFile.writerOptions(conf).setSchema(schema));
            VectorizedRowBatch batch = schema.createRowBatch();
            BytesColumnVector idVector = (BytesColumnVector) batch.cols[0];
            BytesColumnVector dateTimeVector = (BytesColumnVector) batch.cols[1];
            BytesColumnVector fileNameVector = (BytesColumnVector) batch.cols[2];
            BytesColumnVector filePathVector = (BytesColumnVector) batch.cols[3];
            BytesColumnVector binaryVector = (BytesColumnVector) batch.cols[4];
            BytesColumnVector fileMD5Vector = (BytesColumnVector) batch.cols[5];
            LongColumnVector mdLoadIdVector = (LongColumnVector) batch.cols[6];
            BytesColumnVector mdInsTsVector = (BytesColumnVector) batch.cols[7];
            BytesColumnVector mdUpdTsVector = (BytesColumnVector) batch.cols[8];
            BytesColumnVector mdRecCdVector = (BytesColumnVector) batch.cols[9];
            BytesColumnVector mdSrcCdVector = (BytesColumnVector) batch.cols[10];
            BytesColumnVector mdDateVector = (BytesColumnVector) batch.cols[11];
            for(int r=0; r < 1; ++r) {
                int row = batch.size++;
                idVector.setVal(row, id.getBytes());
                dateTimeVector.setVal(row, Timestamp.from(epoch.plusNanos(Math.round(date_time.doubleValue()))).toString().getBytes());
                fileNameVector.setVal(row, file_name.getBytes());
                filePathVector.setVal(row, file_path.getBytes());
                binaryVector.setVal(row, bytes);
                fileMD5Vector.setVal(row, file_md5.getBytes());
                mdLoadIdVector.vector[row] = md_load_id;
                mdInsTsVector.setVal(row, Timestamp.from(epoch.plusNanos(Math.round(md_ins_ts.doubleValue()))).toString().getBytes());
                mdUpdTsVector.setVal(row, Timestamp.from(epoch.plusNanos(Math.round(md_upd_ts.doubleValue()))).toString().getBytes());
                mdRecCdVector.setVal(row, md_rec_cd.getBytes());
                mdSrcCdVector.setVal(row, md_src_cd.getBytes());
                mdDateVector.setVal(row, sdfDate.format(Timestamp.from(epoch.plusNanos(Math.round(md_date.doubleValue())))).getBytes());
                if (batch.size == batch.getMaxSize()) {
                    writer.addRowBatch(batch);
                    batch.reset();
                }
            }
            if (batch.size != 0) {
                writer.addRowBatch(batch);
                batch.reset();
            }
            writer.close();
            synchronized(lock) {
                countLoadFile++;
            }
        } catch (Exception e) {
            writeLogProfile("???????????? ???????????? insertDataToTempTableHive: " + e.toString(), "ERROR", "LOW");
        }
    }

    static void insertDataToTableHive ()   {
        try (
                Connection connection = DriverManager.getConnection(
                        configList.stream().filter((s) -> s.get("environment").equals(environment_hive_in) ).findFirst().get().get("host").toString()
                        , configList.stream().filter((s) -> s.get("environment").equals(environment_hive_in) ).findFirst().get().get("login").toString()
                        , configList.stream().filter((s) -> s.get("environment").equals(environment_hive_in) ).findFirst().get().get("password").toString());
                PreparedStatement pstI = connection.prepareStatement("INSERT INTO TABLE " + table_in + " PARTITION(md_date) SELECT * FROM " + table_in + "_temp");
                PreparedStatement pstD = connection.prepareStatement("DROP TABLE " + table_in + "_temp PURGE");
        ) {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            pstI.execute();
            pstD.execute();
        } catch (Exception e) {
            writeLogProfile("???????????? ???????????????? ???????????? ???? ?????????????????? ??????????????: "  + table_in + "_temp: " + e, "ERROR", "CRITICAL");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (countRows == 0) {
            load_id = md_load_id;
            environment_dwh_in = environment_dwh;
            environment_hdfs_in = environment_hdfs;
            environment_hive_in = environment_hive;
            configList = configListAdd (config_jt);
            table_in = table;
            writeLogProfile("???????????? ???????????????? ????????????", "INFO", "LOW");
            createTempTableHive();
        }
        synchronized(lock) {
            countRows++;
        }
        if(file_path != null) {
            loadFile(id, file_path, date_time, md_load_id, md_ins_ts, md_upd_ts, md_rec_cd, md_src_cd, md_date);
        }
        synchronized(lock) {
            insertDataToTableHive();
            //logInfo("?????????? ?????????????????? ???????????????? ????????????. ???? ???????????? ??????-???? ??????????: " + countRows + ", ?????????????????? ????????????: " + countLoadFile);
            writeLogProfile("?????????? ???????????????? ????????????. ???? ???????????? ??????-???? ??????????: " + countRows + ", ?????????????????? ????????????: " + countLoadFile , "INFO", "LOW");
        }
        //logInfo("?????????? ????????????????????");
    }

    //sout

}