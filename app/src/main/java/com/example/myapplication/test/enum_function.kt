package com.example.myapplication.test

/**
    clean code with Enum
    link: https://medium.com/@roanmonteiro/clean-code-with-java-replace-the-logical-condition-using-enum-if-else-statements-898bd6a85327
 */
enum class TestEnumFunction {

    BUY {
        override fun doSomething() {
            //todo some thing logic here
        }
    },
    SELL {
        override fun doSomething() {
            //todo some thing logic here
        }
    };

    abstract fun doSomething()

    fun abc() {

    }
}