package com.dag.moviestore.ext

inline fun <reified C,reified T> C.tryCatch(
    tryBlock: ()->T?,
    catchBlock: (Throwable) -> Any? = { null },
    finallyBlock: () -> Unit = {}
) = try {
    tryBlock()?.run {
        takeUnless { it is Unit }
    }
}catch (e:Exception){
    catchBlock(e)?.run {
        takeIf { it is T } as? T?
    }
}finally {
    finallyBlock()
}

inline fun <T1 : Any?, T2 : Any?, T3 : Any?, R : Any?> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

inline fun <T1 : Any?, T2 : Any?, R : Any?> safeLet(
    p1: T1?,
    p2: T2?,
    block: (T1, T2) -> R?
): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}