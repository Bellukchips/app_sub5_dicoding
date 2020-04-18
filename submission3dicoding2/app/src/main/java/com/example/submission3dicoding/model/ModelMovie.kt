package com.example.submission3dicoding.model

import org.json.JSONObject

class ModelMovie {
    var name: String? = null
    var photo: String? = null

    var jenis: String? = null
    var id_movie: Int = 0
    var tnggal: String? = null
    var deskripsi: String? = null

    constructor(obj: JSONObject, pindah_fragment: Boolean?) {
        val name: String
        val tnggal: String
        try {
            val id = obj.getInt("id")
            if (pindah_fragment == true) {
                name = obj.getString("title")
                tnggal = obj.getString("release_date")

            } else {

                name = obj.getString("name")
                tnggal = obj.getString("first_air_date")

            }
            val deskripsi = obj.getString("overview")
            val image_path = obj.getString("poster_path")
            this.name = name
            this.deskripsi = deskripsi
            this.photo = image_path
            this.tnggal = tnggal
            this.id_movie = id
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    constructor() {}
}