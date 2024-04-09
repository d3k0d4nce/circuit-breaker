package ru.kishko.client.services;

import ru.kishko.client.enums.Services;

public interface ServiceCallerWithId extends ServiceCaller {
    Services getServiceId();
}
