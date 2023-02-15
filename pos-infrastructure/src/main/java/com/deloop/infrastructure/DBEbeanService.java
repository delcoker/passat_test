package com.deloop.infrastructure;

import io.ebean.Database;

public interface DBEbeanService {
    Database getDb();
}
