/**
 * Copyright (c) 2017-present Laszlo Csontos All rights reserved.
 * <p>
 * This file is part of springuni-particles.
 * <p>
 * springuni-particles is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * springuni-particles is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with springuni-particles.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.deloop.application.models.rest;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by lcsontos on 5/10/17.
 */
@Data
public class RestErrorResponse {

    private final int statusCode;
    private final String reasonPhrase;
    private final String detailedMessage;

    protected RestErrorResponse(HttpStatus status, String detailedMessage) {
        statusCode = status.value();
        reasonPhrase = status.getReasonPhrase();
        this.detailedMessage = detailedMessage;
    }

    public static RestErrorResponse of(HttpStatus status) {
        return of(status, null);
    }

    public static RestErrorResponse of(HttpStatus status, Exception ex) {
        return new RestErrorResponse(status, ex.getMessage());
    }

}
