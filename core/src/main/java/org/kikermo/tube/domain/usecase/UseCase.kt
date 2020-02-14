package com.facechart.core.api.usecases

import io.reactivex.ObservableTransformer

abstract class UseCase<Request, Response> : ObservableTransformer<Request, Response>
