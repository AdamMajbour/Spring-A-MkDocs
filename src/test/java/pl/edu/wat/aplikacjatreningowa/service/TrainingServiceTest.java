package pl.edu.wat.aplikacjatreningowa.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.models.front.TrainingData;
import pl.edu.wat.aplikacjatreningowa.models.main.Training;
import pl.edu.wat.aplikacjatreningowa.repository.TrainingRepository;
import pl.edu.wat.aplikacjatreningowa.repository.UserRepository;
import java.util.List;
import java.util.Optional;

class TrainingServiceTest {

    @Test
    void shouldAddTraining() {
        UserRepository userRepository = new UserRepository() {
            @Override
            public UserAccount findByEmail(String email) {
                return null;
            }

            @Override
            public UserAccount findByLogin(String login) {
                return null;
            }

            @Override
            public List<UserAccount> findAll() {
                return null;
            }

            @Override
            public List<UserAccount> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<UserAccount> findAllById(Iterable<Long> iterable) {
                return null;
            }

            @Override
            public <S extends UserAccount> List<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends UserAccount> S saveAndFlush(S s) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<UserAccount> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public UserAccount getOne(Long aLong) {
                return null;
            }

            @Override
            public <S extends UserAccount> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends UserAccount> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<UserAccount> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends UserAccount> S save(S s) {
                return null;
            }

            @Override
            public Optional<UserAccount> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(UserAccount userAccount) {

            }

            @Override
            public void deleteAll(Iterable<? extends UserAccount> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends UserAccount> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends UserAccount> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends UserAccount> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends UserAccount> boolean exists(Example<S> example) {
                return false;
            }
        };
        UserService userService = new UserService(null, userRepository, null,
                null, null, null, null,
                null, null, null);
        TrainingRepository trainingRepository = new TrainingRepository() {
            @Override
            public Training getTrainingById(Long id) {
                return null;
            }

            @Override
            public List<Training> findAll() {
                return null;
            }

            @Override
            public List<Training> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<Training> findAllById(Iterable<Long> iterable) {
                return null;
            }

            @Override
            public <S extends Training> List<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends Training> S saveAndFlush(S s) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<Training> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public Training getOne(Long aLong) {
                return null;
            }

            @Override
            public <S extends Training> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends Training> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<Training> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Training> S save(S s) {
                return null;
            }

            @Override
            public Optional<Training> findById(Long aLong) {
                return Optional.empty();
            }

            @Override
            public boolean existsById(Long aLong) {
                return false;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void deleteById(Long aLong) {

            }

            @Override
            public void delete(Training training) {

            }

            @Override
            public void deleteAll(Iterable<? extends Training> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends Training> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends Training> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends Training> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends Training> boolean exists(Example<S> example) {
                return false;
            }
        };
        TrainingService trainingService = new TrainingService(trainingRepository, userService,
                null, null);
        TrainingData trainingData = new TrainingData();
        trainingData.setTrainingName("Test");
        trainingData.setTrainingDescription("Test description.");
        Assertions.assertTrue(trainingService.addTraining(trainingData, "test"));
    }
}