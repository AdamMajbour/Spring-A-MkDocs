package pl.edu.wat.aplikacjatreningowa.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.models.front.TrainingFormData;
import pl.edu.wat.aplikacjatreningowa.models.main.TrainingForm;
import pl.edu.wat.aplikacjatreningowa.repository.TrainingFormRepository;
import pl.edu.wat.aplikacjatreningowa.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TrainingFormServiceTest
{

    @Test
    void shouldAddTrainingForm()
    {
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

        TrainingFormRepository trainingFormRepository = new TrainingFormRepository() {
            @Override
            public List<TrainingForm> findAllTrainingForms(Pageable page) {
                return null;
            }

            @Override
            public List<TrainingForm> findAllBetweenDates(Long userid, LocalDateTime beginning, LocalDateTime ending) {
                return null;
            }

            @Override
            public TrainingForm getTrainingFormById(Long id) {
                return null;
            }

            @Override
            public List<TrainingForm> findAll() {
                return null;
            }

            @Override
            public List<TrainingForm> findAll(Sort sort) {
                return null;
            }

            @Override
            public List<TrainingForm> findAllById(Iterable<Long> iterable) {
                return null;
            }

            @Override
            public <S extends TrainingForm> List<S> saveAll(Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends TrainingForm> S saveAndFlush(S s) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<TrainingForm> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public TrainingForm getOne(Long aLong) {
                return null;
            }

            @Override
            public <S extends TrainingForm> List<S> findAll(Example<S> example) {
                return null;
            }

            @Override
            public <S extends TrainingForm> List<S> findAll(Example<S> example, Sort sort) {
                return null;
            }

            @Override
            public Page<TrainingForm> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public <S extends TrainingForm> S save(S s) {
                return null;
            }

            @Override
            public Optional<TrainingForm> findById(Long aLong) {
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
            public void delete(TrainingForm trainingForm) {

            }

            @Override
            public void deleteAll(Iterable<? extends TrainingForm> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends TrainingForm> Optional<S> findOne(Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends TrainingForm> Page<S> findAll(Example<S> example, Pageable pageable) {
                return null;
            }

            @Override
            public <S extends TrainingForm> long count(Example<S> example) {
                return 0;
            }

            @Override
            public <S extends TrainingForm> boolean exists(Example<S> example) {
                return false;
            }
        };
        TrainingFormService trainingFormService = new TrainingFormService(trainingFormRepository, userService,
                null,null,null,null);
        TrainingFormData trainingFormData = new TrainingFormData();
        trainingFormData.setName("Test");
        trainingFormData.setDescription("Test description");
        trainingFormData.setFromDate("2021-06-21T20:00:00+01:00[Europe/Paris]");
        trainingFormData.setToDate("2021-06-21T21:00:00+01:00[Europe/Paris]");
        if (trainingFormService.addTrainingForm(trainingFormData, "test")!=null) Assertions.assertTrue(true);
    }
}