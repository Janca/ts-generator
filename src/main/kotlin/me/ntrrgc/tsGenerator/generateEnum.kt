package me.ntrrgc.tsGenerator

import kotlin.reflect.KClass

fun TypeScriptGenerator.generateEnum(enumClass: KClass<*>): String {
    return when (enumType) {
        EnumType.ENUM_AS_TYPE -> {
            "type ${enumClass.simpleName} = ${enumClass.java.enumConstants
                    .map { it as Enum<*> }
                    .filter { !TypeScriptGenerator.isPropertyIgnored(it) }
                    .joinToString(" | ") {
                        it.name.toJSString()
                    }
            };"
        }

        EnumType.ENUM_AS_ENUM -> {
            val whitespace = "    "
            "enum ${enumClass.simpleName} {\n${enumClass.java.enumConstants
                    .map { it as Enum<*> }
                    .filter { !TypeScriptGenerator.isPropertyIgnored(it) }
                    .joinToString(",\n") {
                        "$whitespace${it.name} = ${it.ordinal}"
                    }
            }\n}"
        }
    }
}