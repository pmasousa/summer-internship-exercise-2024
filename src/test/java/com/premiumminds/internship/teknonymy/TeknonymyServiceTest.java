package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John",'M',null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(expected, result);
  }

  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(expected, result);
  }

  @Test
  public void PersonMultipleChildrenTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{
            new Person("Holy", 'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("Alice", 'F', null, LocalDateTime.of(1045, 1, 1, 0, 0))
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Alice";
    assertEquals(expected, result);
  }

  @Test
  public void PersonGrandchildrenTest() {
    Person grandchild = new Person("Mike", 'M', null, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person child = new Person("Holy", 'F', new Person[]{ grandchild }, LocalDateTime.of(1060, 1, 1, 0, 0));
    Person person = new Person("John", 'M', new Person[]{ child }, LocalDateTime.of(1040, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandfather of Mike";
    assertEquals(expected, result);
  }

  @Test
  public void PersonGreatGrandchildrenTest() {
    Person greatGrandchild = new Person("Sam", 'M', null, LocalDateTime.of(1100, 1, 1, 0, 0));
    Person grandchild = new Person("Mike", 'M', new Person[]{ greatGrandchild }, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person child = new Person("Holy", 'F', new Person[]{ grandchild }, LocalDateTime.of(1060, 1, 1, 0, 0));
    Person person = new Person("John", 'M', new Person[]{ child }, LocalDateTime.of(1040, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandfather of Sam";
    assertEquals(expected, result);
  }

  @Test
  public void PersonGreatGreatGrandchildrenTest() {
    Person greatGreatGrandchild = new Person("Tom", 'M', null, LocalDateTime.of(1120, 1, 1, 0, 0));
    Person greatGrandchild = new Person("Sam", 'M', new Person[]{ greatGreatGrandchild }, LocalDateTime.of(1100, 1, 1, 0, 0));
    Person grandchild = new Person("Mike", 'M', new Person[]{ greatGrandchild }, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person child = new Person("Holy", 'F', new Person[]{ grandchild }, LocalDateTime.of(1060, 1, 1, 0, 0));
    Person person = new Person("John", 'M', new Person[]{ child }, LocalDateTime.of(1040, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-great-grandfather of Tom";
    assertEquals(expected, result);
  }

  @Test
  public void PersonMixedGenerationsTest() {
    Person child1 = new Person("Holy", 'F', null, LocalDateTime.of(1060, 1, 1, 0, 0));
    Person child2 = new Person("Alice", 'F', null, LocalDateTime.of(1070, 1, 1, 0, 0));
    Person person = new Person("John", 'M', new Person[]{ child1, child2 }, LocalDateTime.of(1040, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(expected, result);
  }

  @Test
  public void PersonFemaleGreatGrandmotherTest() {
    Person greatGrandchild = new Person("Sam", 'M', null, LocalDateTime.of(1100, 1, 1, 0, 0));
    Person grandchild = new Person("Mike", 'M', new Person[]{ greatGrandchild }, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person child = new Person("Holy", 'F', new Person[]{ grandchild }, LocalDateTime.of(1060, 1, 1, 0, 0));
    Person person = new Person("Mary", 'F', new Person[]{ child }, LocalDateTime.of(1040, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandmother of Sam";
    assertEquals(expected, result);
  }

  @Test
  public void PersonMultipleGreatGrandchilds() {
    Person greatGrandchild3 = new Person("Sam3", 'M', null, LocalDateTime.of(1103, 1, 1, 0, 0));
    Person greatGrandchild2 = new Person("Sam2", 'M', null, LocalDateTime.of(1102, 1, 1, 0, 0));
    Person greatGrandchild1 = new Person("Sam1", 'M', null, LocalDateTime.of(1101, 1, 1, 0, 0));
    Person grandchild2 = new Person("Mike", 'M', new Person[]{ greatGrandchild1, greatGrandchild2 }, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person grandchild1 = new Person("Mike", 'M', new Person[]{ greatGrandchild3 }, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person child2 = new Person("Holy2", 'F', new Person[]{ grandchild2 }, LocalDateTime.of(1060, 1, 1, 0, 0));
    Person child1 = new Person("Holy1", 'F', new Person[]{ grandchild1 }, LocalDateTime.of(1060, 1, 1, 0, 0));
    Person person = new Person("David", 'M', new Person[]{ child1, child2 }, LocalDateTime.of(1040, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandfather of Sam1";
    assertEquals(expected, result);
  }
}