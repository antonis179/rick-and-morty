package com.amoustakos.rickandmorty.data.domain


interface DomainTransformer<ApiModel, DomainModel> {

    fun transform(response: ApiModel): DomainResponse<DomainModel>?

}