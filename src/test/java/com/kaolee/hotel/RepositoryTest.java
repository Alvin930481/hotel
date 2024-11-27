package com.kaolee.hotel;

import com.kaolee.hotel.pojo.po.UserPO;
import com.kaolee.hotel.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class RepositoryTest {



        @Autowired
        private UserRepository testDataRepository;

        @Test
        public void testMongoConnection() {
            UserPO data = new UserPO();
            data.setName("test");
            testDataRepository.save(data);

            List<UserPO> dataList = testDataRepository.findAll();
            assertFalse(dataList.isEmpty()); // 確認資料被成功儲存
            System.out.println(dataList);
            System.out.println("MongoDB connection is successful!");
        }


}
