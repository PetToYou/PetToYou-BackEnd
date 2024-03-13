-- BaseEntity data is implicitly created with each insert due to its nature of being extended by other entities

-- Inserting data into banner
insert into banner (banner_id, banner_name, banner_link, banner_img, banner_status, banner_type) values
                                                                                                     (1, 'Summer Sale', 'https://example.com/summer-sale', 'https://example.com/summer-sale-img.jpg', 'ACTIVATE', 'MAIN'),
                                                                                                     (2, 'Winter Wellness', 'https://example.com/winter-wellness', 'https://example.com/winter-wellness-img.jpg', 'ACTIVATE', 'HOSPITAL');

-- Inserting data into member
insert into member (member_id, name, nick_name,email, phone, provider, member_status) values
                                                                                   (1, 'John Doe', 'Johnny','email1@naver.com', '01012345678', 'KAKAO', 'ACTIVATE'),
                                                                                   (2, 'Jane Doe', 'Janey', 'email2@gmail.com','01087654321', 'APPLE', 'DORMANT');

-- Inserting data into alarm (Assuming Member data exists)
insert into alarm (alarm_id, member_id, alarm_title, alarm_content, alarm_type, alarm_status) values
                                                                                                  (1, 1, 'Appointment Reminder', 'Your appointment is scheduled for tomorrow.', 'RESERVE_COMPLETE', 'ACTIVATE'),
                                                                                                  (2, 2, 'Welcome to PetCare', 'Thank you for joining PetCare.', 'NEW_MEMBER', 'ACTIVATE');

-- Inserting data into pet (Assuming Member data exists)
insert into pet (pet_id, member_id, pet_name, species, age, birth, adoption_date, is_sharing, pet_status) values
                                                                                                          (1, 1, 'Buddy', 'Dog', 3, '2021-03-10', '2021-03-20', false, 'ACTIVATE'),
                                                                                                          (2, 2, 'Whiskers', 'Cat', 5, '2019-05-05', '2019-05-15', true, 'ACTIVATE');


insert into store (store_id, DTYPE, zip_code, address_detail, sido, sigungu, eupmyun, doro, point)
values
    (1, 'HOSPITAL', 'H', '101번지 Happy Street Building', 'Happy City', 'Joy District', 'eupmyun', 'Happy Street',POINT(126.9780, 37.5665)),
    (2, 'HOSPITAL', 'H', '202번지 Joyful Road Building', 'Joyful City', 'Happy District', 'eupmyun', 'Joyful Road', POINT(36.5678, 127.9820));

insert into hospital (store_id, hospital_name, hospital_phone, notice, additional_service_tag, website_link, hospital_info, hospital_info_photo, hospital_status) values
    (1, 'Happy Pet Clinic', '0201234567', '9AM to 6PM, Closed on Sundays', 'Parking available, Zero-pay accepted', 'http://happypetclinic.com', 'Full veterinary services for your beloved pets.', 'http://happypetclinic.com/photo.jpg', 'ACTIVATE');

-- Inserting data into hospital_tag
insert into hospital_tag (hospital_tag_id, tag_content, tag_type) values
                                                                      (1, '24/7 Emergency', 'SERVICE'),
                                                                      (2, 'Orthopedics Specialist', 'SPECIALITIES');

-- Inserting data into tag_mapper (Assuming Hospital and HospitalTag data exists)
insert into tag_mapper (tag_mapper_id, hospital_tag_id, store_id) values
                                                                         (1, 1, 1),
                                                                         (2, 2, 1);

-- Inserting data into store_photo (Assuming Store data exists)
insert into store_photo (store_photo_id, store_id, store_photo_url, photo_order, photo_status, store_type) values
                                                                                                                        (1, 1, 'https://example.com/store1/photo1.jpg', 1, 'ACTIVATE', 'HOSPITAL'),
                                                                                                                        (2, 1, 'https://example.com/store1/photo2.jpg', 2, 'ACTIVATE', 'HOSPITAL');

-- Inserting data into like (Assuming Member and Store data exists)
insert into scrap (scrap_id, member_id, store_id, store_type) values
                                                                 (1, 1, 1, 'HOSPITAL'),
                                                                 (2, 2, 1, 'SALON');

-- Inserting data into registration_info (Assuming Store data exists)
insert into registration_info (registration_info_id, store_id, store_type, ceo_name, ceo_phone, ceo_email, business_number) values
    (1, 1, 'HOSPITAL', 'CEO Name', '01000000000', 'ceo@example.com', '123-45-67890');

-- Inserting data into reserve (Assuming Store, Pet, and Member data exists)
insert into reserve (reserve_id, store_id, pet_id, member_id, reserve_time, reserve_status) values
    (1, 1, 1, 1, '2024-03-10 12:34:56', 'RESERVE_COMPLETE');

-- Inserting data into business_hour (Assuming Store data exists)
insert into business_hour (business_hour_id, store_id, store_type, day_of_week, start_time, end_time, break_start_time, break_end_time, is_open) values
                                                                                                                                     (8, 1, 'HOSPITAL', 1, '09:00', '18:00', '12:00', '13:00', true), -- 월요일
                                                                                                                                     (2, 1, 'HOSPITAL', 2, '09:00', '19:00', '13:00', '14:00', true), -- 화요일
                                                                                                                                     (3, 1, 'HOSPITAL', 3, '10:00', '17:00', NULL, NULL, true), -- 수요일, 점심시간 없음
                                                                                                                                     (4, 1, 'HOSPITAL', 4, '09:00', '18:00', '12:00', '13:00', true), -- 목요일
                                                                                                                                     (5, 1, 'HOSPITAL', 5, '09:00', '19:00', '12:00', '14:00', true), -- 금요일, 점심시간 길게
                                                                                                                                     (6, 1, 'HOSPITAL', 6, '10:00', '16:00', NULL, NULL, false), -- 토요일, 휴무
                                                                                                                                     (7, 1, 'HOSPITAL', 7, '09:00', '15:00', NULL, NULL, true); -- 일요일, 점심시간 없음

-- Inserting data into review (Assuming Store, Member, and Pet data exists)
insert into review (review_id, store_id, member_id, pet_id, store_type, rating, content, review_status) values
    (1, 1, 1, 1, 'HOSPITAL', 4.5, 'Great service and friendly staff.',  'ACTIVATE');

-- For salon and insurance, you would follow a similar pattern, ensuring Store data exists where necessary
