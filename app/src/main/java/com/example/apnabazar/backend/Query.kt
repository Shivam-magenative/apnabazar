package com.example.apnabazar.backend

import com.shopify.buy3.Storefront
import com.shopify.graphql.support.ID

object Query {
    fun productDefinition(): Storefront.ProductConnectionQueryDefinition {
        return Storefront.ProductConnectionQueryDefinition { productdata ->
            productdata
                .edges { edges ->
                    edges

                        .cursor()
                        .node { node ->
                            node
                                .title()
                                .images({img -> img.first(10)}, {imag->
                                    imag.edges {imgedge ->
                                        imgedge
                                            .node {imgnode ->
                                                imgnode
                                                    .url()
                                                    .height()
                                                    .width()
                                            }
                                    }
                                }
                                )
                                .priceRange({price ->
                                    price
                                        .maxVariantPrice({mPrice->
                                            mPrice
                                                .amount()
                                        })
                                } )
                        }
                }
        }
    }

    fun getProducts(
        cursor: String,
        sortby_key: Storefront.ProductSortKeys?,
        direction: Boolean,
        number: Int,
        ): Storefront.QueryRootQuery {
        val shoppro: Storefront.QueryRootQuery.ProductsArgumentsDefinition
        if (cursor == "nocursor") {
            if (sortby_key != null) {
                shoppro = Storefront.QueryRootQuery.ProductsArgumentsDefinition { args ->
                    args.first(number).sortKey(sortby_key).reverse(direction)
                }
            } else {
                shoppro = Storefront.QueryRootQuery.ProductsArgumentsDefinition { args ->
                    args.first(number).reverse(direction)
                }
            }
        } else {
            if (sortby_key != null) {
                shoppro = Storefront.QueryRootQuery.ProductsArgumentsDefinition { args ->
                    args.first(number).after(cursor).sortKey(sortby_key).reverse(direction)
                }
            } else {
                shoppro = Storefront.QueryRootQuery.ProductsArgumentsDefinition { args ->
                    args.first(number).after(cursor).reverse(direction)
                }
            }
        }

            return Storefront.query { root ->
                root.products(
                    shoppro,
                    productDefinition()
                )

//                    ) { rootnode ->
//                        rootnode.onCollection { oncollection ->
//                            oncollection
//                                .handle()
//                                .image { image ->
//                                    image
//                                        .url()
//                                        .width().height()
//                                }
//                                .title()
//                                .products(
//                                    definition, productDefinition()
//                                )
//                        }
//                    }
            }
    }


    fun  getCollections(cursor: String): Storefront.QueryRootQuery {
        val definition: Storefront.QueryRootQuery.CollectionsArgumentsDefinition
        if (cursor == "nocursor") {
            definition =
                Storefront.QueryRootQuery.CollectionsArgumentsDefinition { args -> args.first(250).sortKey(
                    Storefront.CollectionSortKeys.UPDATED_AT).reverse(true) }
        } else {
            definition = Storefront.QueryRootQuery.CollectionsArgumentsDefinition { args ->
                args.first(250).after(cursor).sortKey(Storefront.CollectionSortKeys.UPDATED_AT).reverse(true)
            }
        }
        return Storefront.query { root ->
            root.collections(definition, collectiondef)
        }
    }
    private val collectiondef: Storefront.CollectionConnectionQueryDefinition
        get() = Storefront.CollectionConnectionQueryDefinition { collect ->
            collect
                .edges({ edge ->
                    edge
                        .cursor()
                        .node({ node ->
                            node.title()
                                .seo { it->
                                    it.title().description()
                                }
                            .image({
                                        image ->
                                    image
                                        .url()
                                        .width()
                                        .height()
                                })
                        })
                })
                .pageInfo(Storefront.PageInfoQueryDefinition { it.hasNextPage()
                    .startCursor()
                    .endCursor()})
        }
}






