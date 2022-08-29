package com.tdonuk.passwordmanager.domain;

/** @author Taha Donuk
 * This is used to configure firebase queries. These values are not actually a part of the querying process
 * but we are using these in switch-cases and creating the firebase queries according to them
 */
public enum QueryType {
    STARTS_WITH, ENDS_WITH, BEFORE, AFTER, BIGGER_THAN, SMALLER_THAN, CONTAINS, EQUALS
}
