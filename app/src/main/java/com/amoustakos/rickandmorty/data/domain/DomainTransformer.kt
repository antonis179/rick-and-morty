package com.amoustakos.rickandmorty.data.domain


interface DomainTransformer<ApiModel, DomainModel, D> {

    fun transform(response: ApiModel, data: D): DomainResponse<DomainModel>?

}