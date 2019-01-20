//$CLASS$$METHOD$$ID$RetryMethod
package com.jrymos.common.dynamic.source;

import $IMPORT$;
import com.jrymos.common.base.retry.RetryMethod;

public class $CLASS$$METHOD$$ID$RetryMethod implements RetryMethod {

    private $CLASS$ service;

    public $CLASS$$METHOD$$ID$RetryMethod($CLASS$ service) {
        this.service = service;
    }

    @Override
    public void run(Object... args) {
        service.$METHOD$($PARAMETERS$);
    }
}

