package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;

class TeknonymyService implements ITeknonymyService {

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name 
   */
  public String getTeknonymy(Person person) {
    String TeknonymyName = "";
    if (person.children() == null) {
      return TeknonymyName;
    }
    
    int childDepth = getLowestDepth(person);

    Person oldestChild = getOldestChild(person, childDepth, 0);

    if (childDepth > 2) {
      for (int i = 0; i < childDepth - 2; i++) {
        TeknonymyName += "great-";
      }
      TeknonymyName += "grand";
    }
    if (person.sex() == 'M') {
      TeknonymyName += "father";
    } else {
      TeknonymyName += "mother";
    }
    TeknonymyName += " of " + oldestChild.name();

    return TeknonymyName;
  };

  private Integer getLowestDepth(Person person) {
    int lowestDepth = 0;
    if (person.children() == null) {
      return lowestDepth;
    }
    for (Person child : person.children()) {
      int childDepth = getLowestDepth(child);
      if (childDepth > lowestDepth) {
        lowestDepth = childDepth;
      }
    }
    return lowestDepth + 1;
  }

  private Person getOldestChild(Person person, int depth, int currentDepth) {
    Person oldestChild = null;
    Person currentChild = null;
    if (currentDepth == depth) {
      return person;
    }
    if (person.children() == null) {
      return null;
    }
    for (Person child : person.children()) {
      currentChild = getOldestChild(child, depth, currentDepth + 1);
      if (currentChild == null) {
        continue;
      }
      if (oldestChild == null) {
        oldestChild = currentChild;
      } else {
        if (currentChild.dateOfBirth().isBefore(oldestChild.dateOfBirth())) {
          oldestChild = currentChild;
        }
      }
    }
    return oldestChild;
  }
}
